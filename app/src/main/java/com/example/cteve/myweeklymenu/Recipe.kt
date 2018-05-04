package com.example.cteve.myweeklymenu

import android.os.Parcelable
import java.io.Serializable

/**
 * Created by klphillips on 5/4/2018.
 */

class Recipe (//Name of the recipe?
        var name: String, //Breakfast for that day?
        var breakfast: Boolean, //Lunch for that day?
        var lunch: Boolean, //Dinner for that?
        var dinner: Boolean, //Recipe itself
        var recipe: String

) {

    override fun toString(): String {
        var string = name + "\n" + recipe
        return string
    }
}
