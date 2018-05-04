package com.example.cteve.myweeklymenu;

/**
 * Created by klphillips on 5/4/2018.
 */

public class Day {
    //What day of the week?
    private String dayOfWeek;
    //Breakfast for that day?
    Boolean breakfast;
    //Lunch for that day?
    Boolean lunch;
    //Dinner for that?
    Boolean dinner;

    Day(String day, Boolean b, Boolean l, Boolean d){
        dayOfWeek = day;
        breakfast = b;
        lunch = l;
        dinner = d;
    }

    public String getDayOfWeek(){ return dayOfWeek;}

    public Boolean getBreakfast() {return breakfast;}

    public Boolean getLunch(){return lunch;}

    public Boolean getDinner(){return dinner;}

    public void setDayOfWeek(String d){dayOfWeek = d;}

    public void setBreakfast(Boolean b){breakfast = b;}

    public void setLunch(Boolean l){lunch =l;}

    public void setDinner(Boolean d){dinner=d;}

}
