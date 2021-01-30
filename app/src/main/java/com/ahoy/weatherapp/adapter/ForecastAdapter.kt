package com.ahoy.weatherapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.base.BaseRecyclerViewAdapter
import com.ahoy.weatherapp.base.BaseViewHolder
import com.ahoy.weatherapp.callbacks.CardSelectionListener
import com.ahoy.weatherapp.databinding.WeatherItemLayoutBinding
import com.ahoy.weatherapp.fragments.DetailFragment
import com.ahoy.weatherapp.repo.local.model.Forcast
import kotlinx.android.synthetic.main.weather_item_layout.view.*
import java.lang.ref.WeakReference

class ForecastAdapter(val forecasts: List<Forcast>) :
    BaseRecyclerViewAdapter<Forcast, WeatherItemLayoutBinding>() {

    private var listener = WeakReference<CardSelectionListener>(null)

    init {
        addItems(forecasts)
    }

    fun addListener(listener : CardSelectionListener){
        this.listener = WeakReference(listener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<WeatherItemLayoutBinding>, position: Int) {
        onBind(holder.binding, itemList[position])
    }

    private fun onBind(binding: WeatherItemLayoutBinding, forcast: Forcast) {
        binding.forecast = forcast
        binding.root.cvWeather.setOnClickListener {
            listener.get()?.onItemSelected(forcast.dt)
        }

    }

    override fun getLayoutId(): Int = R.layout.weather_item_layout


}

