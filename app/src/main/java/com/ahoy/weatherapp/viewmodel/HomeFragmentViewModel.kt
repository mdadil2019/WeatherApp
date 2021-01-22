package com.ahoy.weatherapp.viewmodel

import android.location.Location
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahoy.weatherapp.constants.Temprature
import com.ahoy.weatherapp.adapter.ForecastAdapter
import com.ahoy.weatherapp.repo.WeatherRepository
import com.ahoy.weatherapp.repo.local.model.WeatherResponse
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HomeFragmentViewModel(
    private val weatherRepository : WeatherRepository
) : ViewModel() {
    val weatherResponse : ObservableField<WeatherResponse> = ObservableField()

    var forecastAdapter : ForecastAdapter = ForecastAdapter(emptyList())
    var temprature : MutableLiveData<Temprature> = MutableLiveData()

    init {
        getCurrentWeather()
        getForecast()
    }

    private fun getCurrentWeather(){
        weatherRepository.getCurrentWeather {
            weatherResponse.set(it)
            temprature.value = Temprature(valueInKelvin = it.main.temp.roundToInt())
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

    fun onUnitChange(){
        temprature.value!!.changeUnit()
        temprature.postValue(temprature.value!!)
    }

}