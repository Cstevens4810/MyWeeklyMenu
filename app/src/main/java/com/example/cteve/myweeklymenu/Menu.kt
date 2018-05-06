package com.example.cteve.myweeklymenu

/**
 * Created by KyraPhillips on 5/4/2018.
 */

class Menu( var bMonday: BooleanArray,  var bTuesday: BooleanArray,  var bWednesday: BooleanArray,  var bThursday: BooleanArray,  var bFriday: BooleanArray,  var bSaturday: BooleanArray,  var bSunday: BooleanArray,  var rMonday: Array<Recipe>,  var rTuesday: Array<Recipe>,  var rWednesday: Array<Recipe>,  var rThursday: Array<Recipe>,  var rFriday: Array<Recipe>,  var rSaturday: Array<Recipe>,  var rSunday: Array<Recipe>) {

    fun getbMonday(): BooleanArray {
        return bMonday
    }

    fun setbMonday(bMonday: BooleanArray) {
        this.bMonday = bMonday
    }

    fun getbTuesday(): BooleanArray {
        return bTuesday
    }

    fun setbTuesday(bTuesday: BooleanArray) {
        this.bTuesday = bTuesday
    }

    fun getbWednesday(): BooleanArray {
        return bWednesday
    }

    fun setbWednesday(bWednesday: BooleanArray) {
        this.bWednesday = bWednesday
    }

    fun getbThursday(): BooleanArray {
        return bThursday
    }

    fun setbThursday(bThursday: BooleanArray) {
        this.bThursday = bThursday
    }

    fun getbFriday(): BooleanArray {
        return bFriday
    }

    fun setbFriday(bFriday: BooleanArray) {
        this.bFriday = bFriday
    }

    fun getbSaturday(): BooleanArray {
        return bSaturday
    }

    fun setbSaturday(bSaturday: BooleanArray) {
        this.bSaturday = bSaturday
    }

    fun getbSunday(): BooleanArray {
        return bSunday
    }

    fun setbSunday(bSunday: BooleanArray) {
        this.bSunday = bSunday
    }

    fun getrMonday(): Array<Recipe> {
        return rMonday
    }

    fun setrMonday(rMonday: Array<Recipe>) {
        this.rMonday = rMonday
    }

    fun getrTuesday(): Array<Recipe> {
        return rTuesday
    }

    fun setrTuesday(rTuesday: Array<Recipe>) {
        this.rTuesday = rTuesday
    }

    fun getrWednesday(): Array<Recipe> {
        return rWednesday
    }

    fun setrWednesday(rWednesday: Array<Recipe>) {
        this.rWednesday = rWednesday
    }

    fun getrThursday(): Array<Recipe> {
        return rThursday
    }

    fun setrThursday(rThursday: Array<Recipe>) {
        this.rThursday = rThursday
    }

    fun getrFriday(): Array<Recipe> {
        return rFriday
    }

    fun setrFriday(rFriday: Array<Recipe>) {
        this.rFriday = rFriday
    }

    fun getrSaturday(): Array<Recipe> {
        return rSaturday
    }

    fun setrSaturday(rSaturday: Array<Recipe>) {
        this.rSaturday = rSaturday
    }

    fun getrSunday(): Array<Recipe> {
        return rSunday
    }

    fun setrSunday(rSunday: Array<Recipe>) {
        this.rSunday = rSunday
    }

    override fun toString(): String {
         var string = "Monday: " + rMonday[0].name + " , " + rMonday[1].name + " , " + rMonday[2].name + "\n"
         string = string + "Tuesday: " + rTuesday[0].name + " , " + rTuesday[1].name + " , " + rTuesday[2].name + "\n"
         string = string + "Wednesday: " + rWednesday[0].name + " , " + rWednesday[1].name + " , " + rWednesday[2].name + "\n"
         string = string + "Thursday: " + rThursday[0].name + " , " + rThursday[1].name + " , " + rThursday[2].name + "\n"
         string = string + "Friday: " + rFriday[0].name + " , " + rFriday[1].name + " , " + rFriday[2].name + "\n"
         string = string + "Saturday: " + rSaturday[0].name + " , " + rSaturday[1].name + " , " + rSaturday[2].name + "\n"
        string = string + "Sunday: " + rSunday[0].name + " , " + rSunday[1].name + " , " + rSunday[2].name
        return string
    }
}
