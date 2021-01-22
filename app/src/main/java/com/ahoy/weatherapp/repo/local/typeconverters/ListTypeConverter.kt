package com.ahoy.weatherapp.repo.local.typeconverters

import androidx.room.TypeConverter
import com.ahoy.weatherapp.repo.local.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ListTypeConverter {

    @TypeConverter
    fun toListOfWeather(value : String): List<Weather>{
        val gson = Gson()
        val listType = object : TypeToken<List<Weather>>(){}.type

        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toJsonStringFromWeatherList(value: List<Weather>) : String{
        val gson = Gson()
        return gson.toJson(value)


    }


}