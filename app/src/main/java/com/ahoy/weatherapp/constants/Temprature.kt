package com.ahoy.weatherapp.constants

import kotlin.math.roundToInt


data class Temprature (
    var unit : Unit = Unit.DegreeCelcieus,
    var valueInKelvin : Int,
    var result : Int = 0
){
    fun changeUnit(){
        if(unit == Unit.Fahrenheit){
            result = (valueInKelvin - ("273.15").toFloat()).roundToInt()
            unit = Unit.DegreeCelcieus
        }else{
            result = ((valueInKelvin - 273.15) * 9/5  + 32).roundToInt()
            unit = Unit.Fahrenheit
        }
    }

    fun getTemp():String{
        if(unit == Unit.DegreeCelcieus){
            return "$result °C"
        }
        return "$result °F"
    }

}