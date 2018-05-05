package com.example.cteve.myweeklymenu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button


class MainActivity : AppCompatActivity() {

    var foundRecipe = Recipe("",false,false,false,"")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

