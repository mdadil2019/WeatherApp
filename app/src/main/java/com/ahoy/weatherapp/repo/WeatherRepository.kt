package com.ahoy.weatherapp.repo

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.ahoy.weatherapp.repo.local.WeatherHuntDatabase
import com.ahoy.weatherapp.repo.local.model.Forcast
import com.ahoy.weatherapp.repo.local.model.WeatherResponse
import com.ahoy.weatherapp.repo.remote.Networking
import com.ahoy.weatherapp.repo.remote.Networking.WEATHER_BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.security.acl.Owner

class WeatherRepository(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner
) {
    private val db = WeatherHuntDatabase.getInstance(context)

    suspend fun requestForecasts(location: Location) {
        withContext(Dispatchers.IO) {
            val response = Networking.create(WEATHER_BASE_URL)
                .queryWeeklyForecast(location.latitude, location.longitude).body()

            response?.let {
                it.daily.forEach { forcast ->
                    Log.e("DEMO FORECAST", "$forcast")
                    db.forecastDao().insertForecast(forcast)
                }
            }
        }
    }

    fun getForecasts(onSuccess: (List<Forcast>) -> Unit) {
        db.forecastDao().getWeeklyForecast().observe(lifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                onSuccess(it)
            }
        })
    }


    suspend fun requestCurrentWeather(location: Location) {
        withContext(Dispatchers.IO) {
            val response = Networking.create(WEATHER_BASE_URL)
                .queryCurrentWeather(location.latitude, location.longitude).body()
            Log.e("DEMO", "$response")
            response?.let {
                db.currentWeatherDao().insertCurrentWeather(it)
            }
        }
    }

    fun getCurrentWeather(onSuccess: (WeatherResponse) -> Unit) {
        db.currentWeatherDao().getCurrentWeather().observe(lifecycleOwner, Observer {
            if(it != null){
                onSuccess(it)
            }
        })
    }

}