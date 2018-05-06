package com.example.cteve.myweeklymenu

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_generate_menu.*
import java.util.*
import android.widget.Toast
import android.view.View


class generateMenu : AppCompatActivity() {
    var recipes = ArrayList<Recipe>()
    var random = Random()
    var menu : com.example.cteve.myweeklymenu.Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_menu)
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if(prefs.getString("potato",null)!=null) {
            getList()
        }
        val button = findViewById<Button>(R.id.generateButton)
        button.setOnClickListener { generateMenu() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        val generate = menu!!.findItem(R.id.action_generate)
        generate.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

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

    fun mondayButton(view :View){
        var monBBox = findViewById<CheckBox>(R.id.monBreakBox)
        var monLBox = findViewById<CheckBox>(R.id.monLunBox)
        var monDBox = findViewById<CheckBox>(R.id.monDinBox)

        monBBox.isChecked = true
        monLBox.isChecked = true
        monDBox.isChecked = true

    }

    fun tuesdayButton(view :View){

        var tueBBox = findViewById<CheckBox>(R.id.tuesBreakBox)
        var tueLBox = findViewById<CheckBox>(R.id.tuesLunBox)
        var tueDBox = findViewById<CheckBox>(R.id.tuesDinBox)
        tueBBox.isChecked = true
        tueLBox.isChecked = true
        tueDBox.isChecked = true

    }

    fun wednesdayButton(view :View){

        var wedBBox = findViewById<CheckBox>(R.id.wednsBreakBox)
        var wedLBox = findViewById<CheckBox>(R.id.wednsLunBox)
        var wedDBox = findViewById<CheckBox>(R.id.wednsDinBox)

        wedBBox.isChecked = true
        wedLBox.isChecked = true
        wedDBox.isChecked = true

    }

    fun thursdayButton(view :View){
        var thurBBox = findViewById<CheckBox>(R.id.thursBreakBox)
        var thurLBox = findViewById<CheckBox>(R.id.thursLunBox)
        var thurDBox = findViewById<CheckBox>(R.id.thursDinBox)

        thurBBox.isChecked = true
        thurLBox.isChecked = true
        thurDBox.isChecked = true

    }

    fun fridayButton(view :View){
        var friBBox = findViewById<CheckBox>(R.id.friBreakBox)
        var friLBox = findViewById<CheckBox>(R.id.friLunBox)
        var friDBox = findViewById<CheckBox>(R.id.friDinBox)
        friBBox.isChecked = true
        friLBox.isChecked = true
        friDBox.isChecked = true
    }

    fun saturdayButton(view :View){
        var satBBox = findViewById<CheckBox>(R.id.satBreakBox)
        var satLBox = findViewById<CheckBox>(R.id.satLunBox)
        var satDBox = findViewById<CheckBox>(R.id.satDinBox)
        satBBox.isChecked = true
        satLBox.isChecked = true
        satDBox.isChecked = true

    }

    fun sundayButton(view :View){

        var sunBBox = findViewById<CheckBox>(R.id.sunBreakBox)
        var sunLBox = findViewById<CheckBox>(R.id.sunLunBox)
        var sunDBox = findViewById<CheckBox>(R.id.sunDinBox)
        sunBBox.isChecked = true
        sunLBox.isChecked = true
        sunDBox.isChecked = true


    }

    fun generateMenu(){
        var monBBox = findViewById<CheckBox>(R.id.monBreakBox)
        var monLBox = findViewById<CheckBox>(R.id.monLunBox)
        var monDBox = findViewById<CheckBox>(R.id.monDinBox)
        var tueBBox = findViewById<CheckBox>(R.id.tuesBreakBox)
        var tueLBox = findViewById<CheckBox>(R.id.tuesLunBox)
        var tueDBox = findViewById<CheckBox>(R.id.tuesDinBox)
        var wedBBox = findViewById<CheckBox>(R.id.wednsBreakBox)
        var wedLBox = findViewById<CheckBox>(R.id.wednsLunBox)
        var wedDBox = findViewById<CheckBox>(R.id.wednsDinBox)
        var thurBBox = findViewById<CheckBox>(R.id.thursBreakBox)
        var thurLBox = findViewById<CheckBox>(R.id.thursLunBox)
        var thurDBox = findViewById<CheckBox>(R.id.thursDinBox)
        var friBBox = findViewById<CheckBox>(R.id.friBreakBox)
        var friLBox = findViewById<CheckBox>(R.id.friLunBox)
        var friDBox = findViewById<CheckBox>(R.id.friDinBox)
        var satBBox = findViewById<CheckBox>(R.id.satBreakBox)
        var satLBox = findViewById<CheckBox>(R.id.satLunBox)
        var satDBox = findViewById<CheckBox>(R.id.satDinBox)
        var sunBBox = findViewById<CheckBox>(R.id.sunBreakBox)
        var sunLBox = findViewById<CheckBox>(R.id.sunLunBox)
        var sunDBox = findViewById<CheckBox>(R.id.sunDinBox)

        var bMonday : BooleanArray = booleanArrayOf(false, false, false)
        var bTuesday : BooleanArray = booleanArrayOf(false, false, false)
        var bWednesday : BooleanArray = booleanArrayOf(false, false, false)
        var bThursday : BooleanArray = booleanArrayOf(false, false, false)
        var bFriday : BooleanArray = booleanArrayOf(false, false, false)
        var bSaturday : BooleanArray = booleanArrayOf(false, false, false)
        var bSunday : BooleanArray = booleanArrayOf(false, false, false)

        if (monBBox.isChecked){
            bMonday[0] = true
        }
        if (monLBox.isChecked){
            bMonday[1] = true
        }
        if (monDBox.isChecked){
            bMonday[2] = true
        }
        if (tueBBox.isChecked){
            bTuesday[0] = true
        }
        if (tueLBox.isChecked){
            bTuesday[1] = true
        }
        if (tueDBox.isChecked) {
            bTuesday[2] = true
        }
        if (wedBBox.isChecked) {
            bWednesday[0] = true
        }
        if (wedLBox.isChecked){
            bWednesday[1] = true
        }
        if (wedDBox.isChecked){
            bWednesday[2] = true
        }
        if (thurBBox.isChecked){
            bThursday[0] = true
        }
        if (thurLBox.isChecked) {
            bThursday[1] = true
        }
        if (thurDBox.isChecked){
            bThursday[2] = true
        }
        if (friBBox.isChecked){
            bFriday[0] = true
        }
        if(friLBox.isChecked) {
            bFriday[1] = true
        }
        if(friDBox.isChecked){
            bFriday[2] = true
        }
        if (satBBox.isChecked){
            bSaturday[0] = true
        }
        if (satLBox.isChecked){
            bSaturday[1] = true
        }
        if (satDBox.isChecked){
            bSaturday[2] = true
        }
        if (sunBBox.isChecked){
            bSunday[0] = true
        }
        if (sunLBox.isChecked){
            bSunday[1] = true
        }
        if (sunDBox.isChecked){
            bSunday[2] = true
        }


        var bList = ArrayList<Recipe>()
        var lList = ArrayList<Recipe>()
        var dList = ArrayList<Recipe>()


        for (recipe in recipes){
            if (recipe.breakfast){
                bList.add(recipe)}
            if (recipe.lunch){
                lList.add(recipe) }
            if (recipe.dinner){
                dList.add(recipe) }
        }


        var recipe = Recipe("", false, false, false, "")

        var rMonday : Array<Recipe> = arrayOf(recipe, recipe, recipe)
        var rTuesday : Array<Recipe> = arrayOf(recipe, recipe, recipe)
        var rWednesday : Array<Recipe> = arrayOf(recipe, recipe, recipe)
        var rThursday :Array<Recipe> = arrayOf(recipe, recipe, recipe)
        var rFriday :Array<Recipe> = arrayOf(recipe, recipe, recipe)
        var rSaturday :Array<Recipe> = arrayOf(recipe, recipe, recipe)
        var rSunday : Array<Recipe> = arrayOf(recipe, recipe, recipe)

        var bLength = bList.size
        var lLength = lList.size
        var dLength = dList.size

        for (recipe in bList){
            if (bMonday[0]){
                var spot1 = randomBetween(0,bLength)
                rMonday[0] = bList[spot1]
            }
            if (bTuesday[0]){
                var spot2 = randomBetween(0,bLength)
                rTuesday[0] = bList[spot2]
            }
            if (bWednesday[0]){
                var spot3 = randomBetween(0,bLength)
                rWednesday[0] = bList[spot3]
            }
            if (bThursday[0]){
                var spot4 = randomBetween(0,bLength)
                rThursday[0] = bList[spot4]
            }
            if (bFriday[0]){
                var spot5 = randomBetween(0,bLength)
                rFriday[0] = bList[spot5]}
            if (bSaturday[0]){
                var spot6 = randomBetween(0,bLength)
                rSaturday[0] = bList[spot6]
            }
            if (bSunday[0]){
                var spot7 = randomBetween(0,bLength)
                rSunday[0] = bList[spot7]
            }
        }

        for (recipe in lList){
            if (bMonday[1]){
                var spot1 = randomBetween(0,lLength)
                rMonday[1] = lList[spot1]
            }
            if (bTuesday[1]){
                var spot2 = randomBetween(0,lLength)
                rTuesday[1] = lList[spot2]
            }
            if (bWednesday[1]){
                var spot3 = randomBetween(0,lLength)
                rWednesday[1] = lList[spot3]
            }
            if (bThursday[1]){
                var spot4 = randomBetween(0,lLength)
                rThursday[1] = lList[spot4]
            }
            if (bFriday[1]){
                var spot5 = randomBetween(0,lLength)
                rFriday[1] = lList[spot5]}
            if (bSaturday[1]){
                var spot6 = randomBetween(0,lLength)
                rSaturday[1] = lList[spot6]
            }
            if (bSunday[1]){
                var spot7 = randomBetween(0,lLength)
                rSunday[1] = lList[spot7]
            }
        }

        for (recipe in dList){
            if (bMonday[2]){
                var spot1 = randomBetween(0,dLength)
                rMonday[2] = dList[spot1]
            }
            if (bTuesday[2]){
                var spot2 = randomBetween(0,dLength)
                rTuesday[2] = dList[spot2]
            }
            if (bWednesday[2]){
                var spot3 = randomBetween(0,dLength)
                rWednesday[2] = dList[spot3]
            }
            if (bThursday[2]){
                var spot4 = randomBetween(0,dLength)
                rThursday[2] = dList[spot4]
            }
            if (bFriday[2]){
                var spot5 = randomBetween(0,dLength)
                rFriday[2] = dList[spot5]}
            if (bSaturday[2]){
                var spot6 = randomBetween(0,dLength)
                rSaturday[2] = dList[spot6]
            }
            if (bSunday[2]){
                var spot7 = randomBetween(0,dLength)
                rSunday[2] = dList[spot7]
            }
        }

        menu = Menu(bMonday, bTuesday, bWednesday, bThursday, bFriday, bSaturday, bSunday, rMonday, rTuesday, rWednesday, rThursday, rFriday, rSaturday, rSunday)

        Thread.sleep(1000)
        sendMenu()

    }

    fun sendMenu(){
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("menuList", menu.toString())
        startActivity(intent)

    }

    fun getList() {

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = prefs.getString("potato", null)

        val list: ArrayList<Recipe> = gson.fromJson(json, object: TypeToken<ArrayList<Recipe>>() {}.type)

        recipes = list
    }

    fun randomBetween(min: Int, max: Int): Int {
        return random.nextInt(max - min) + min
    }
}

