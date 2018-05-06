package com.example.cteve.myweeklymenu

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.JsonReader
import android.util.Log
import android.view.*
import android.view.Menu
import android.widget.*
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.vision.v1.Vision
import com.google.api.services.vision.v1.VisionRequestInitializer
import com.google.api.services.vision.v1.model.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipe_list.*
import kotlinx.android.synthetic.main.dialog_layout.*
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*


class recipeList : AppCompatActivity(), GestureDetector.OnGestureListener {

    var recipes = ArrayList<Recipe>()

    private var bitmap: Bitmap? = null
    private var contentURI: Uri = Uri.EMPTY
    private var vision: Vision? = null
    private var gDescription = java.util.ArrayList<String>()       //holds image description info from Google
    private var imageText: String? = "One Second. Getting your recipe..."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if (prefs.getString("potato", null) != null) {
            getList()
        } else {
        }
        display()
        //restore image if there is one (use after a device rotation)
        if (savedInstanceState != null && savedInstanceState[IMAGE_KEY] != null) {
            contentURI = savedInstanceState[IMAGE_KEY] as Uri
            showImage(contentURI)
        }
        setupVisionAPI()
    }

    private fun setupVisionAPI() {
        val visionBuilder = Vision.Builder(
                NetHttpTransport(),
                AndroidJsonFactory(),
                null)
        //the following uses my assigned API-key
        visionBuilder.setVisionRequestInitializer(
                VisionRequestInitializer(API_KEY))
        vision = visionBuilder.build()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_toolbar, menu)
        val recipe = menu!!.findItem(R.id.action_list)
        recipe.isVisible = false
        return true
    }

    override fun onStop() {
        super.onStop()
        saveList(recipes)
    }

    override fun onPause() {
        super.onPause()
        saveList(recipes)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_addRecipe -> {
            addRecipe()
            true
        }
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        R.id.action_generate -> {
            val intent = Intent(this, generateMenu::class.java)
            this.startActivity(intent)
            true
        }

        R.id.action_list -> {
            val intent = Intent(this, recipeList::class.java)
            this.startActivity(intent)
            true
        }

        R.id.action_main -> {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun addRecipe() {
        val inflater = LayoutInflater.from(this@recipeList)
        val subView = inflater.inflate(R.layout.dialog_layout, null)

        val builder = AlertDialog.Builder(this@recipeList)
        builder.setTitle("Add Recipe")

        val nameText = subView.findViewById<EditText>(R.id.recipeName) as EditText
        val boxB = subView.findViewById<CheckBox>(R.id.breakfastBox)
        val boxL = subView.findViewById<CheckBox>(R.id.lunchBox)
        val boxD = subView.findViewById<CheckBox>(R.id.dinnerBox)
        val btnCamera = subView.findViewById<Button>(R.id.cameraButton) as Button
        val btnLibrary = subView.findViewById<Button>(R.id.libraryButton) as Button
        val detailsText = subView.findViewById<EditText>(R.id.recipeDetails) as EditText

        nameText.text = null
        detailsText.text = null
        var breakfast = false
        var lunch = false
        var dinner = false

        /*
        boxB.setOnCheckedChangeListener { buttonView, isChecked ->
            breakfast = true
        }
        boxL.setOnCheckedChangeListener { buttonView, isChecked ->
            lunch = true
        }
        boxD.setOnCheckedChangeListener { buttonView, isChecked ->
            dinner = true
        }
        */

        btnLibrary.setOnClickListener {
            Log.i(TAG, "GETTING IMAGE")
            getAnImage()
            btnCamera.visibility = View.VISIBLE
        }
        //Toast.makeText(applicationContext,"Allow time for text import", Toast.LENGTH_LONG).show()

        btnCamera.setOnClickListener {
            //Toast.makeText(applicationContext,imageText, Toast.LENGTH_LONG).show()
            detailsText.setText(imageText, TextView.BufferType.EDITABLE)
        }

        builder.setPositiveButton("Save Recipe") { dialog, which ->

            var rName = nameText.text.toString()
            var rDetails = detailsText.text.toString()
            var newRecipe = Recipe(rName, breakfast, lunch, dinner, rDetails)
            var text = newRecipe.toString()
            Toast.makeText(applicationContext, newRecipe.toString(), Toast.LENGTH_LONG).show()
            recipes.add(newRecipe)
            display()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.setView(subView)

        val dialog: AlertDialog = builder.create()
        dialog.show()
        fun enableButton()
        {
            if(breakfast || lunch || dinner)
            {
                if( nameText.text != null && detailsText.text != null)
                {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                }
            }
        }
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

        boxB.setOnCheckedChangeListener { buttonView, isChecked ->
            breakfast = true
            enableButton()
        }
        boxL.setOnCheckedChangeListener { buttonView, isChecked ->
            lunch = true
            enableButton()
        }
        boxD.setOnCheckedChangeListener { buttonView, isChecked ->
            dinner = true
            enableButton()
        }


    }


    fun display(){
        val bList = findViewById<ListView>(R.id.breakfastList)
        val lList = findViewById<ListView>(R.id.lunchList)
        val dList = findViewById<ListView>(R.id.dinnerList)

        var tempBList = ArrayList<String>()
        var tempLList = ArrayList<String>()
        var tempDList = ArrayList<String>()
        for (recipe in recipes){
            if (recipe.breakfast){
                tempBList.add(recipe.name)
            }
            if (recipe.lunch){
                tempLList.add(recipe.name)
            }
            if (recipe.dinner){
                tempDList.add(recipe.name)
            }
        }

        val adapterB = ArrayAdapter(this, android.R.layout.simple_list_item_1, tempBList)
        bList.adapter = adapterB

        val adapterL = ArrayAdapter(this, android.R.layout.simple_list_item_1, tempLList)
        lList.adapter = adapterL

        val adapterD = ArrayAdapter(this, android.R.layout.simple_list_item_1, tempDList)
        dList.adapter = adapterD

        bList.onItemLongClickListener = AdapterView.OnItemLongClickListener{ parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Log.i("---REMOVE---", "LONG TOUCH DETECTED")
            val builder = AlertDialog.Builder(this@recipeList)
            Log.i("---REMOVE---", "BUILDER MADE")
            builder.setTitle("Delete " + selectedItem + "?")
            Log.i("---REMOVE---", "TITLE MADE")
            builder.setPositiveButton("Delete") { dialog, which ->
                var removeRecipe: Recipe? = null
                for (recipe in recipes) {
                    Log.i("---REMOVE---", "LOOKING FOR RECIPE " + recipe.name)
                    if (recipe.name == selectedItem) {
                        Log.i("---REMOVE---", "REMOVE " + recipe.name)
                        builder.setMessage(recipe.recipe)
                        removeRecipe = recipe
                    }
                }
                Log.i("---REMOVE---", "REMOVING RECIPE")
                recipes.remove(removeRecipe)
                display()
            }
            val dialog: AlertDialog = builder.create()
            Log.i("---REMOVE---", "SHOWING DIALOG")
            dialog.show()

            true
        }

        lList.onItemLongClickListener = AdapterView.OnItemLongClickListener{ parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Log.i("---REMOVE---", "LONG TOUCH DETECTED")
            val builder = AlertDialog.Builder(this@recipeList)
            Log.i("---REMOVE---", "BUILDER MADE")
            builder.setTitle("Delete " + selectedItem + "?")
            Log.i("---REMOVE---", "TITLE MADE")
            builder.setPositiveButton("Delete") { dialog, which ->
                var removeRecipe: Recipe? = null
                for (recipe in recipes) {
                    Log.i("---REMOVE---", "LOOKING FOR RECIPE " + recipe.name)
                    if (recipe.name == selectedItem) {
                        Log.i("---REMOVE---", "REMOVE " + recipe.name)
                        builder.setMessage(recipe.recipe)
                        removeRecipe = recipe
                    }
                }
                Log.i("---REMOVE---", "REMOVING RECIPE")
                recipes.remove(removeRecipe)
                display()
            }
            val dialog: AlertDialog = builder.create()
            Log.i("---REMOVE---", "SHOWING DIALOG")
            dialog.show()

            true
        }

        dList.onItemLongClickListener = AdapterView.OnItemLongClickListener{ parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Log.i("---REMOVE---", "LONG TOUCH DETECTED")
            val builder = AlertDialog.Builder(this@recipeList)

            builder.setTitle("Delete " + selectedItem + "?")
            Log.i("---REMOVE---", "TITLE MADE")
            builder.setPositiveButton("Delete") { dialog, which ->
                var removeRecipe: Recipe? = null
                for (recipe in recipes) {
                    Log.i("---REMOVE---", "LOOKING FOR RECIPE " + recipe.name)
                    if (recipe.name == selectedItem) {
                        Log.i("---REMOVE---", "REMOVE " + recipe.name)
                        builder.setMessage(recipe.recipe)
                        removeRecipe = recipe
                    }
                }
                Log.i("---REMOVE---", "REMOVING RECIPE")
                recipes.remove(removeRecipe)
                display()
            }
            val dialog: AlertDialog = builder.create()
            Log.i("---REMOVE---", "SHOWING DIALOG")
            dialog.show()

            true
        }

        bList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String

            // Display the selected item text on TextView
            val builder = AlertDialog.Builder(this@recipeList)
            builder.setTitle(selectedItem)
            for (recipe in recipes){
                if (recipe.name == selectedItem){
                    builder.setMessage(recipe.recipe)
                }
            }
            val dialog: AlertDialog = builder.create()

            dialog.show()

        }


        lList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String

            // Display the selected item text on TextView
            val builder = AlertDialog.Builder(this@recipeList)
            builder.setTitle(selectedItem)
            for (recipe in recipes){
                if (recipe.name == selectedItem){
                    builder.setMessage(recipe.recipe)
                }
            }
            val dialog: AlertDialog = builder.create()

            dialog.show()

        }

        dList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String

            // Display the selected item text on TextView
            val builder = AlertDialog.Builder(this@recipeList)
            builder.setTitle(selectedItem)
            for (recipe in recipes){
                if (recipe.name == selectedItem){
                    builder.setMessage(recipe.recipe)
                }
            }
            val dialog: AlertDialog = builder.create()

            dialog.show()

        }
    }


    fun returnRecipeList(){
        var recipe2Find = ""
        var foundRecipe = ArrayList<String>()
        if (intent != null){
            if(intent.extras != null){
                recipe2Find = intent.extras.getString("recipeName")
            }
        }

        for (recipe in recipes){
            if (recipe.name == recipe2Find){
                foundRecipe[0] = recipe.name
                foundRecipe[1] = recipe.recipe
            }
        }

        val intent2 = Intent(this, MainActivity::class.java)
        intent2.putExtra("foundRecipe", foundRecipe)
        startActivity(intent2)


    }

    //
    fun saveList(list:ArrayList<Recipe>)
    {
        //Toast.makeText(applicationContext,"poop", Toast.LENGTH_LONG).show()

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val editor : SharedPreferences.Editor = prefs.edit()

        val gson = Gson()
        val json:String = gson.toJson(list)
        editor.putString("potato", json)
        editor.apply()
    }

    fun getList() {

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = prefs.getString("potato", null)

        val list: ArrayList<Recipe> = gson.fromJson(json, object: TypeToken<ArrayList<Recipe>>() {}.type)

        recipes = list
    }

    inner class GVisionThread: AsyncTask<String, String, String>() {

        private var returnedInfo = ""
        private var text: TextAnnotation? = null

        override fun doInBackground(vararg arg: String?):String {
            Log.i(TAG, "GVisionThread")

            val bArray:ByteArray? = bitmap?.convertToByteArray()  //convertToByteArray defined below
            val myImg: Image = Image()
            myImg.encodeContent(bArray)
            Log.i(TAG,myImg.toString())
            val desiredFeature = Feature()
            desiredFeature.type = "DOCUMENT_TEXT_DETECTION"

            val request = AnnotateImageRequest()
            request.image = myImg
            request.features = Arrays.asList(desiredFeature)

            val batchRequest = BatchAnnotateImagesRequest()
            batchRequest.requests = Arrays.asList(request)

            Log.i(TAG, "GVisionThread - batchRequest is built")

            //  Talk to Google and get a description of the image
            Log.i(TAG, "GVisionThread - batchResponse being created")
            val batchResponse = vision?.images()?.annotate(batchRequest)?.execute()

            //Pause here waiting for Google Vision to respond

            Log.i(TAG, "GVisionThread - POOOOOOOOOOP")
            if (batchResponse != null) {
                Log.i(TAG, "GVisionThread - batchResponse is built")
            } else {
                Log.i(TAG, "GVisionThread - batchResponse is null ----    <--------  error")
            }

            //returns a list of entityAnnotation to facts
            Log.i(TAG, "Number returned is ${batchResponse!!.responses.size}, respose[0] is ${batchResponse.responses[0].toString()}")


            returnedInfo = "${batchResponse.responses[0]}"     //returned JSon info
            /*
           facts = batchResponse.responses[0].labelAnnotations       //list of picture labels*/

            text = batchResponse.responses[0].fullTextAnnotation
            return returnedInfo
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            buildNewDescription(text)
        }
    }

    private fun getTextFromImage()
    {
        Log.i(TAG,"GETTING TEXT FROM IMAGE")
        GVisionThread().execute()

    }

    private fun buildNewDescription(text: TextAnnotation?)
    {
        Log.i(TAG,"pooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooop")
        val inflater = LayoutInflater.from(this@recipeList)
        val subView = inflater.inflate(R.layout.dialog_layout, null)
        val detailsText = subView.findViewById<EditText>(R.id.recipeDetails) as EditText
        imageText = text!!.text
        detailsText.setText("poooooooooooop", TextView.BufferType.EDITABLE)
        Log.i(TAG,"ssssssssssssssssssssssssssssssssssssssssssssssssssssssss")

    }


    private fun getAnImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    /**
     * Called by the Android system when the image's URI is returned from the Gallery
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }
        if (data != null) {
            contentURI = data.data
            Log.i(TAG,"GOT THE IMAGE")
            Toast.makeText(applicationContext,"Allow time for text import", Toast.LENGTH_LONG).show()
            showImage(contentURI)
            getTextFromImage()
        }
    }
    /**
     * Save the image in the Bundle so it can be recovered if image is rotated
     * @param state The Bundle
     */
    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        Log.i(TAG, "onSaveInstanceState:")
        state.putParcelable(IMAGE_KEY, contentURI)
    }
    /**
     * Convert bitmap to byte array using ByteBuffer.
     * From Thomas Ivan at:
     * https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
     */
    private fun Bitmap.convertToByteArray(): ByteArray {
        //minimum number of bytes that can be used to store this bitmap's pixels
        val size = this.byteCount
        Log.i(TAG, "convertToByteArray(): size = $size")

        //allocate new instances which will hold bitmap
        //var bytes = ByteArray(size)
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bytes = stream.toByteArray()
        return bytes
    }
    private fun showImage(uri: Uri) {
        try {

            val bmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            val w1 = bmap.byteCount
            val wid = bmap.width
            val factor = wid / GOAL_SIZE    //amount to reduce picture by
            Log.i(TAG, "Need to reduce image by a factor of $factor")
            bitmap = Bitmap.createScaledBitmap(bmap, bmap.width / factor, bmap.height / factor, false)
            //bitmap = bmap
            val w2 = bitmap!!.byteCount
            Log.i(TAG, "Bytecounts are: $w1 and $w2, width = $wid")
            //the_image.setImageBitmap(bitmap)        //show on screen
            Toast.makeText(this@recipeList, "Image Saved!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this@recipeList, "Retrieval of image Failed!", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "******ERROR***** - Retrieval of image failed")
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        return true
    }

    override fun onFling(event1: MotionEvent, event2: MotionEvent,
                         velocityX: Float, velocityY: Float): Boolean {
        return true
    }

    override fun onLongPress(event: MotionEvent) {

    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent,
                          distanceX: Float, distanceY: Float): Boolean {
        return true
    }

    override fun onShowPress(event: MotionEvent) {
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        return true
    }


    ////////////////////////  Decode Json /////////////////////////

    fun readJsonStream(inStream: InputStream) {
        val isr = InputStreamReader(inStream, "UTF-8")
        val reader = JsonReader(isr)
        readDescriptionArray(reader);
    }

    @Throws(IOException::class)
    fun readDescriptionArray(reader: JsonReader) {
        gDescription = java.util.ArrayList<String>()

        reader.beginArray()             //skip past Json '['
        while (reader.hasNext()) {
            gDescription.add(readMessage(reader))   //pass next object from the array to readMessage()
        }
        reader.endArray()
        Log.i(TAG, "readDescriptionArray\n${gDescription.toString()}")
    }

    /**
     * parses one line of the description info
     * @param reader The JsonReader tht has the information
     * @returns A String containing the object's name and score
     */
    @Throws(IOException::class)
    fun readMessage(reader: JsonReader): String {
        var desc: String = ""
        var mid: String = ""
        var score: Double = 0.0
        var topicality: Double = 0.0


        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            if (name == "description") {
                desc = reader.nextString()
            } else if (name == "mid") {
                mid = reader.nextString()
            } else if (name == "score") {
                score = reader.nextDouble()
            } else if (name == "topicality") {
                topicality = reader.nextDouble()
            } else {
                reader.skipValue()
            }
        }
        reader.endObject()
        return "%.2f".format(score) + "\t $desc"
    }

    companion object {
        private const val TAG = "--Text Reader----"
        private const val GALLERY = 7734
        private const val IMAGE_KEY = "TheImage"
        private const val GOAL_SIZE = 400       //good image width size for quick response from Google
        private const val API_KEY = "AIzaSyAAuYiKsbxj9LReThNAkAmDgwLSQuBqBU4 "
    }
}

