package com.ahoy.weatherapp.viewmodel

import android.location.Location
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahoy.weatherapp.adapter.DetailForecastAdapter
import com.ahoy.weatherapp.adapter.ForecastAdapter
import com.ahoy.weatherapp.repo.WeatherRepository
import com.ahoy.weatherapp.repo.local.model.Forcast
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DetailFragmentViewModel @ViewModelInject constructor(
    private var weatherRepository : WeatherRepository
) : ViewModel() {
    var description : ObservableField<String> = ObservableField()
    val lastFetchedTime : ObservableField<String> = ObservableField()

    fun observerForecastOfTheDay(date : Long) : LiveData<Forcast> =
            weatherRepository.getForecastForDay(date)

    fun observerForecast():LiveData<List<Forcast>> =  weatherRepository.getForecasts()
}