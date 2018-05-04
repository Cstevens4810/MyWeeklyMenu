package com.example.cteve.myweeklymenu;

/**
 * Created by klphillips on 5/4/2018.
 */

public class Recipe {

    //Breakfast for that day?
    Boolean breakfast;
    //Lunch for that day?
    Boolean lunch;
    //Dinner for that?
    Boolean dinner;
    //Recipe itself
    private String recipe;

    Recipe(Boolean b, Boolean l, Boolean d, String r){
        breakfast = b;
        lunch = l;
        dinner = d;
        recipe = r;
    }


    public Boolean getBreakfast() {return breakfast;}

    public Boolean getLunch(){return lunch;}

    public Boolean getDinner(){return dinner;}

    public String getRecipe(){return recipe;}

    public void setRecipe(String rec){recipe = rec;}

    public void setBreakfast(Boolean b){breakfast = b;}

    public void setLunch(Boolean l){lunch =l;}

    public void setDinner(Boolean d){dinner=d;}
}
