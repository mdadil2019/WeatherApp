package com.ahoy.weatherapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.base.BaseRecyclerViewAdapter
import com.ahoy.weatherapp.base.BaseViewHolder
import com.ahoy.weatherapp.databinding.WeatherItemLayoutBinding
import com.ahoy.weatherapp.repo.local.model.Forcast
import kotlinx.android.synthetic.main.weather_item_layout.view.*

class ForecastAdapter(val forecasts: List<Forcast>) :
    BaseRecyclerViewAdapter<Forcast, WeatherItemLayoutBinding>() {


    init {
        addItems(forecasts)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<WeatherItemLayoutBinding>, position: Int) {
        onBind(holder.binding, itemList[position])
    }

    private fun onBind(binding: WeatherItemLayoutBinding, forcast: Forcast) {
        binding.forecast = forcast
        binding.root.cvWeather.setOnClickListener {
            showWeatherFragment(binding, forcast)
        }

    }

    private fun showWeatherFragment(binding: WeatherItemLayoutBinding, forecast: Forcast) {
//        val activity = binding.root.context as AppCompatActivity
//        val weatherFragment = WeatherFragment()
//        //add Object Data to fragment
//        val args = Bundle()
//        args.putParcelable("DATA_FORECAST", forecast)
//        weatherFragment.arguments = args
//
//        activity.supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainer, weatherFragment).addToBackStack("backstack").commit()

    }

    override fun getLayoutId(): Int = R.layout.weather_item_layout


}