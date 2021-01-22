package com.ahoy.weatherapp.repo.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Entity
data class WeatherResponse(
    var base: String,

    @Embedded
    var clouds: Clouds,
    var cod: Int,

    @Embedded
    var coord: Coord,

    @PrimaryKey
    var dt: Int,
    var id: Int,

    @Embedded
    var main: Main,
    var name: String,

    @Embedded
    var sys: Sys,
    var timezone: Int,

    var weather: List<Weather>,

    @Embedded
    var wind: Wind,


    var lastFetchedTime : String?
){
    data class Clouds(
        var all: Int
    )

    data class Coord(
        var lat: Double,
        var lon: Double
    )

    data class Main(
        var feels_like: Double,
        var grnd_level: Int,
        var humidity: Int,
        var pressure: Int,
        var sea_level: Int,
        var temp: Double,
        var temp_max: Double,
        var temp_min: Double
    ){
        fun getTempInCelcius(): String{
            return (temp_max - ("273.15").toFloat()).roundToInt().toString() + "°C"
        }

        fun getHumidityInString(): String{
            return "$humidity%"
        }


    }

    data class Sys(
        var country: String,
        var sunrise: Int,
        var sunset: Int
    )




    data class Wind(
        var deg: Int,
        var speed: Double


    ){


        fun getWindSpeedInString(): String{
            return (speed.toFloat()).roundToInt().toString() + "Km/h"
        }
    }

    fun updateLastFetchedTime(){
        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var formattedDate: String = dateFormat.format(System.currentTimeMillis())
        lastFetchedTime = formattedDate
    }


}