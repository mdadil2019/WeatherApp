package com.ahoy.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.viewmodel.MyViewModelFactory
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.adapter.DetailForecastAdapter
import com.ahoy.weatherapp.databinding.FragmentDetailBinding
import com.ahoy.weatherapp.repo.WeatherRepository
import com.ahoy.weatherapp.viewmodel.DetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    lateinit var viewModel : DetailFragmentViewModel
    lateinit var binding : FragmentDetailBinding
    var detailForecastAdapter : DetailForecastAdapter = DetailForecastAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate<FragmentDetailBinding>(layoutInflater,
            R.layout.fragment_detail,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
            MyViewModelFactory(
                DetailFragmentViewModel::class
            ) {
                DetailFragmentViewModel(WeatherRepository(activity!!.applicationContext))
            }).get(DetailFragmentViewModel::class.java)

        binding.viewModel = viewModel
        setupAdapter()
        getSelectedDate()

    }

    private fun getSelectedDate() {
        arguments?.getLong("forecast_selected_item_date")?.let { date ->
            observeWeatherDescription(date)
        }
    }

    private fun observeWeatherDescription(date: Long) {
        viewModel.observerForecastOfTheDay(date).observe(viewLifecycleOwner, Observer {
            viewModel.description.set("The wind speed is ${it.getWindSpeedInString()} and the humidity is ${it.getHumidityInString()}. " +
                    "The weather seems ${it.weather[0].description}")
        })
    }

    private fun setupAdapter() {
        rvDetailForecast.layoutManager = LinearLayoutManager(context!!,
                RecyclerView.VERTICAL, false)
        rvDetailForecast.adapter = detailForecastAdapter
        observeForecast()
    }

    private fun observeForecast() {
        viewModel.observerForecast().observe(viewLifecycleOwner, Observer {
            detailForecastAdapter.addItems(it)
        })
    }
}