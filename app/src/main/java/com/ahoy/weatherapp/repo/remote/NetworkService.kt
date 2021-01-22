package com.ahoy.weatherapp.repo.remote

import com.ahoy.weatherapp.repo.local.model.ForecastResponse
import com.ahoy.weatherapp.repo.local.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET(EndPoints.CURRENT_WEATHER_END_POINT)
    suspend fun queryCurrentWeather(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("appid") appId : String = EndPoints.WEATHER_API_KEY
    ): Response<WeatherResponse>

    @GET(EndPoints.FORCAST_ENDPOINT)
    suspend fun queryWeeklyForecast(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("exclude") excluded : String = "hourly,current",
        @Query("appid") appId : String = EndPoints.WEATHER_API_KEY
    ) : Response<ForecastResponse>
}