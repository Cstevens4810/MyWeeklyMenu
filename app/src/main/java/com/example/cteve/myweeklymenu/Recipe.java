package com.example.cteve.myweeklymenu;

/**
 * Created by klphillips on 5/4/2018.
 */

public class Recipe {
    //Name of the recipe?
    private String name;
    //Breakfast for that day?
    private Boolean breakfast;
    //Lunch for that day?
    private Boolean lunch;
    //Dinner for that?
    private Boolean dinner;
    //Recipe itself
    private String recipe;

    Recipe(String n, Boolean b, Boolean l, Boolean d, String r){
        name = n;
        breakfast = b;
        lunch = l;
        dinner = d;
        recipe = r;
    }

    public String getName(){return name;}

    public Boolean getBreakfast() {return breakfast;}

    public Boolean getLunch(){return lunch;}

    public Boolean getDinner(){return dinner;}

    public String getRecipe(){return recipe;}

    public void setName(String n){name = n;}

    public void setRecipe(String rec){recipe = rec;}

    public void setBreakfast(Boolean b){breakfast = b;}

    public void setLunch(Boolean l){lunch =l;}

    public void setDinner(Boolean d){dinner=d;}
}
