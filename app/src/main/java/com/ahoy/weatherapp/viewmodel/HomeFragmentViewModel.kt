package com.ahoy.weatherapp.viewmodel

import android.location.Location
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.adapter.ForecastAdapter
import com.ahoy.weatherapp.repo.WeatherRepository
import com.ahoy.weatherapp.repo.local.model.WeatherResponse
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val weatherRepository : WeatherRepository
) : ViewModel() {
    val weatherResponse : ObservableField<WeatherResponse> = ObservableField()

    var forecastAdapter : ForecastAdapter = ForecastAdapter(emptyList())

    init {
        getCurrentWeather()
        getForecast()
    }

    private fun getCurrentWeather(){
        weatherRepository.getCurrentWeather {
            weatherResponse.set(it)
        }
    }

    fun fetchCurrentWeather(location : Location){
        viewModelScope.launch {
            weatherRepository.requestCurrentWeather(location)
        }
    }

    fun fetchForecast(location: Location){
        viewModelScope.launch {
            weatherRepository.requestForecasts(location)
        }
    }

    fun getForecast(){
        weatherRepository.getForecasts {
            forecastAdapter.addItems(it)
        }
    }

}