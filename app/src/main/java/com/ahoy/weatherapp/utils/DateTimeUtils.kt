package com.ahoy.weatherapp.utils

import timber.log.Timber
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun getFormattedDate(timestamp: Long) : String{
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis = timestamp * 1000L
        val dayName = getDay(calendar.get(Calendar.DAY_OF_WEEK))



        Timber.e("date : $timestamp")
        val dateFormat = SimpleDateFormat("dd-MMM", Locale.getDefault())
        val formattedDate: String = dateFormat.format(timestamp * 1000L)
        Timber.e("final date = $formattedDate")
        return  "$dayName, $formattedDate"

    }

    private fun getDay(day : Int): String? {
        val days = mutableMapOf<Int,String>().apply {
            put(1,"Sun")
            put(2,"Mon")
            put(3,"Tue")
            put(4,"Wed")
            put(5,"Thr")
            put(6,"Fri")
            put(7,"Sat")
        }

        return days[day]

    }

    fun getTime(timeStamp : Int){

    }
}