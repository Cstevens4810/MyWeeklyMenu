package com.example.cteve.myweeklymenu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.content.DialogInterface



class recipeList : AppCompatActivity() {

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
        val items = arrayOf<CharSequence>("Breakfast", "Lunch", "Dinner")
        val builder = AlertDialog.Builder(this@recipeList)
        builder.setTitle("Add Recipe")

        builder.setSingleChoiceItems(items, -1, DialogInterface.OnClickListener { dialog, item -> Toast.makeText(applicationContext, items[item], Toast.LENGTH_SHORT).show() })

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun display(){


    }
}

