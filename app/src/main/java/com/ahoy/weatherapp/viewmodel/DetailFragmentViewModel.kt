package com.ahoy.weatherapp.viewmodel

import android.location.Location
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahoy.weatherapp.adapter.DetailForecastAdapter
import com.ahoy.weatherapp.adapter.ForecastAdapter
import com.ahoy.weatherapp.repo.WeatherRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DetailFragmentViewModel(
    private var weatherRepository : WeatherRepository
) : ViewModel() {

    var description : ObservableField<String> = ObservableField()
    val lastFetchedTime : ObservableField<String> = ObservableField()
    var detailForecastAdapter : DetailForecastAdapter = DetailForecastAdapter(emptyList())

    fun getForecastOfDay(date : Long){
        Timber.e("forecast query date : $date")
        weatherRepository.getForecastForDay(date) {
            Timber.e("query return success $it")
            description.set("The wind speed is ${it.getWindSpeedInString()} and the humidity is ${it.getHumidityInString()}. " +
                    "The weather seems ${it.weather[0].description}")
        }
    }

    fun getForecast(){
        weatherRepository.getForecasts {
            detailForecastAdapter.addItems(it)
        }
    }
}