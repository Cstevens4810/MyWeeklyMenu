package com.example.cteve.myweeklymenu

/**
 * Created by KyraPhillips on 5/4/2018.
 */

class Menu( var bMonday: Array<Boolean>,  var bTuesday: Array<Boolean>,  var bWednesday: Array<Boolean>,  var bThursday: Array<Boolean>,  var bFriday: Array<Boolean>,  var bSaturday: Array<Boolean>,  var bSunday: Array<Boolean>,  var rMonday: Array<Recipe>,  var rTuesday: Array<Recipe>,  var rWednesday: Array<Recipe>,  var rThursday: Array<Recipe>,  var rFriday: Array<Recipe>,  var rSaturday: Array<Recipe>,  var rSunday: Array<Recipe>, var breakfastCount: Int, var lunchCount: Int, var dinnerCount: Int) {

    fun getbMonday(): Array<Boolean> {
        return bMonday
    }

    fun setbMonday(bMonday: Array<Boolean>) {
        this.bMonday = bMonday
    }

    fun getbTuesday(): Array<Boolean> {
        return bTuesday
    }

    fun setbTuesday(bTuesday: Array<Boolean>) {
        this.bTuesday = bTuesday
    }

    fun getbWednesday(): Array<Boolean> {
        return bWednesday
    }

    fun setbWednesday(bWednesday: Array<Boolean>) {
        this.bWednesday = bWednesday
    }

    fun getbThursday(): Array<Boolean> {
        return bThursday
    }

    fun setbThursday(bThursday: Array<Boolean>) {
        this.bThursday = bThursday
    }

    fun getbFriday(): Array<Boolean> {
        return bFriday
    }

    fun setbFriday(bFriday: Array<Boolean>) {
        this.bFriday = bFriday
    }

    fun getbSaturday(): Array<Boolean> {
        return bSaturday
    }

    fun setbSaturday(bSaturday: Array<Boolean>) {
        this.bSaturday = bSaturday
    }

    fun getbSunday(): Array<Boolean> {
        return bSunday
    }

    fun setbSunday(bSunday: Array<Boolean>) {
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
}
