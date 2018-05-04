package com.example.cteve.myweeklymenu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.*
import kotlinx.android.synthetic.main.activity_recipe_list.*
import kotlinx.android.synthetic.main.dialog_layout.*


class recipeList : AppCompatActivity() {

    var recipes = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_toolbar, menu)
        val recipe = menu!!.findItem(R.id.action_list)
        recipe.isVisible = false
        display()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_addRecipe ->{
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

        R.id.action_list->{
            val intent = Intent(this, recipeList::class.java)
            this.startActivity(intent)
            true
        }

        R.id.action_main->{
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun addRecipe(){
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

        var breakfast = false
        var lunch = false
        var dinner = false

        boxB.setOnCheckedChangeListener { buttonView, isChecked ->
            breakfast = true
        }
        boxL.setOnCheckedChangeListener { buttonView, isChecked ->
            lunch = true
        }
        boxD.setOnCheckedChangeListener { buttonView, isChecked ->
            dinner = true
        }

        builder.setPositiveButton("Save Recipe") { dialog, which ->
            var rName = nameText.text.toString()
            var rDetails = detailsText.text.toString()
            var newRecipe = Recipe(rName, breakfast, lunch, dinner, rDetails)
            var text = newRecipe.toString()

            Toast.makeText(applicationContext,newRecipe.toString(), Toast.LENGTH_LONG).show()
            recipes.add(newRecipe)
            display()
        }

        builder.setNegativeButton("Cancel") {dialog, which ->
            dialog.cancel()
        }

        builder.setView(subView)

        val dialog: AlertDialog = builder.create()
        dialog.show()
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
    }

    fun returnRecipeList():ArrayList<Recipe>{
        return recipes

    }
}

