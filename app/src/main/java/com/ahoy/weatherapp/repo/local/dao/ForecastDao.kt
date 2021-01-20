package com.ahoy.weatherapp.repo.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahoy.weatherapp.repo.local.model.Forcast

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecast: Forcast)

    @Query("SELECT * FROM Forcast ORDER BY dt LIMIT 7")
    fun getWeeklyForecast(): LiveData<List<Forcast>>


}