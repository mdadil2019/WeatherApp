package com.ahoy.weatherapp.repo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahoy.weatherapp.repo.local.dao.ForecastDao
import com.ahoy.weatherapp.repo.local.dao.WeatherResponseDao
import com.ahoy.weatherapp.repo.local.model.Forcast
import com.ahoy.weatherapp.repo.local.model.WeatherResponse
import com.ahoy.weatherapp.repo.local.typeconverters.ListTypeConverter

@Database(entities  = [WeatherResponse::class, Forcast::class], version = 1)
@TypeConverters(ListTypeConverter::class)
abstract class WeatherHuntDatabase : RoomDatabase() {

    abstract fun forecastDao() : ForecastDao
    abstract fun currentWeatherDao() : WeatherResponseDao

    companion object{
        const val DB_NAME  = "weatherhunt.db"

        @Volatile
        private var INSTANCE : WeatherHuntDatabase? = null

        fun getInstance(context : Context) : WeatherHuntDatabase{
            if(INSTANCE == null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherHuntDatabase::class.java,
                        DB_NAME
                    ).build()
                    return INSTANCE!!
                }
            }
            return INSTANCE!!
        }

    }
}