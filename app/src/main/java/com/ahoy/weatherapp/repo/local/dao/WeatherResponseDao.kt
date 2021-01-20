package com.ahoy.weatherapp.repo.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahoy.weatherapp.repo.local.model.WeatherResponse

@Dao
interface WeatherResponseDao {

    @Query("SELECT * FROM WeatherResponse")
    fun getCurrentWeather() : LiveData<WeatherResponse>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(currentWeatherResponse: WeatherResponse)
}