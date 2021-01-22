package com.ahoy.weatherapp.adapter

import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.base.BaseRecyclerViewAdapter
import com.ahoy.weatherapp.base.BaseViewHolder
import com.ahoy.weatherapp.databinding.DetailItemLayoutBinding
import com.ahoy.weatherapp.databinding.WeatherItemLayoutBinding
import com.ahoy.weatherapp.repo.local.model.Forcast
import kotlinx.android.synthetic.main.weather_item_layout.view.*

class DetailForecastAdapter(val forecasts: List<Forcast>) :
    BaseRecyclerViewAdapter<Forcast, DetailItemLayoutBinding>() {

    init {
        addItems(forecasts)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<DetailItemLayoutBinding>, position: Int) {
        onBind(holder.binding, itemList[position])
    }

    private fun onBind(binding: DetailItemLayoutBinding, forcast: Forcast) {
        binding.forecast = forcast
    }

    override fun getLayoutId(): Int = R.layout.detail_item_layout
}
