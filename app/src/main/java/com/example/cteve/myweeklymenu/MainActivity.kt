package com.example.cteve.myweeklymenu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    var foundRecipe = Recipe("",false,false,false,"")
    var menuList = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent!=null){
            if (intent.extras != null){
                menuList = intent.extras.getString("menuList")
                display()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        val main = menu!!.findItem(R.id.action_main)
        main.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_addRecipe ->{

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

    //Function to find the recipe when button is clicked
    //Parameters: The name of the recipe as a string
    //Returns a recipe object
    fun findRecipe(recipeName: String): ArrayList<String> {
        var recipe = ArrayList<String>()
        val intent1 = Intent(this, recipeList::class.java)
        intent1.putExtra("recipeName", recipeName)
        startActivity(intent1)

        if (intent!=null){
            if (intent.extras != null){
                recipe = intent.extras.getStringArrayList("recipeName")
            }
        }

        return recipe

    }

    fun display(){
        var mondayText = findViewById<TextView>(R.id.monMenu)
        var tuesdayText = findViewById<TextView>(R.id.tueMenu)
        var wednesdayText = findViewById<TextView>(R.id.wedMenu)
        var thursdayText = findViewById<TextView>(R.id.thurMenu)
        var fridayText = findViewById<TextView>(R.id.friMenu)
        var saturdayText = findViewById<TextView>(R.id.satMenu)
        var sundayText = findViewById<TextView>(R.id.SunMenu)

        var days = menuList.split("\n")
        var mondayString = ""
        var tuesdayString = ""
        var wednesdayString = ""
        var thursdayString = ""
        var fridayString = ""
        var saturdayString = ""
        var sundayString = ""

        for (day in days) {
            if (day.contains("Monday")) {
                var mon = day.split(":")
                var rec = mon[1]
                var stuff = rec.split(",")
                for (things in stuff) {
                    if (things != "  ") {
                        if (things != stuff[stuff.size - 1]) {
                            mondayString += things + "\n"
                        } else {
                            mondayString += things
                        }
                    }
                }
            }
            if (day.contains("Tuesday")) {
                var tue = day.split(":")
                var rec = tue[1]
                var stuff = rec.split(",")
                for (things in stuff) {
                    if (things != "  ") {
                        if (things != stuff[stuff.size - 1]) {
                            tuesdayString += things + "\n"
                        } else {
                            tuesdayString += things
                        }
                    }
                }
            }
            if (day.contains("Wednesday")) {
                var wed = day.split(":")
                var rec = wed[1]
                var stuff = rec.split(",")
                for (things in stuff) {
                    if (things != "  ") {
                        if (things != stuff[stuff.size - 1]) {
                            wednesdayString += things + "\n"
                        } else {
                            wednesdayString += things
                        }
                    }
                }
            }
            if (day.contains("Thursday")) {
                var thurs = day.split(":")
                var rec = thurs[1]
                var stuff = rec.split(",")
                for (things in stuff) {
                    if (things != "  ") {
                        if (things != stuff[stuff.size - 1]) {
                            thursdayString += things + "\n"
                        } else {
                            thursdayString += things
                        }
                    }
                }
            }
            if (day.contains("Friday")) {
                var fri = day.split(":")
                var rec = fri[1]
                var stuff = rec.split(",")
                for (things in stuff) {
                    if (things != "  ") {
                        if (things != stuff[stuff.size - 1]) {
                            fridayString += things + "\n"
                        } else {
                            fridayString += things
                        }
                    }
                }
            }
            if (day.contains("Saturday")) {
                var sat = day.split(":")
                var rec = sat[1]
                var stuff = rec.split(",")
                for (things in stuff) {
                    if (things != "  ") {
                        if (things != stuff[stuff.size - 1]) {
                            saturdayString += things + "\n"
                        } else {
                            saturdayString += things
                        }
                    }
                }
            }
            if (day.contains("Sunday")) {
                var sun = day.split(":")
                var rec = sun[1]
                var stuff = rec.split(",")
                for (things in stuff) {
                    if (things != "  ") {
                        if (things != stuff[stuff.size - 1]) {
                            sundayString += things + "\n"
                        } else {
                            sundayString += things
                        }
                    }
                }
            }
        }

        mondayText.text = mondayString
        tuesdayText.text = tuesdayString
        wednesdayText.text = wednesdayString
        thursdayText.text = thursdayString
        fridayText.text = fridayString
        saturdayText.text = saturdayString
        sundayText.text = sundayString


    }

    //Function to display the recipe when button is clicked
    //Parameters: Button that was clicked
    fun displayRecipe(view: View) {
        val button: Button
        button = view as Button
        val recipeName = button.text.toString()
        var foundRecipe = findRecipe(recipeName)
        val recipe = foundRecipe

        //Display the recipe using an AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle(foundRecipe[0])
        builder.setMessage(foundRecipe[1])

        builder.setPositiveButton("Ok") { dialog, which ->
            dialog.cancel()
        }

    }
}

