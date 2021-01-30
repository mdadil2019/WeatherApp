package com.ahoy.weatherapp.fragments

import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.viewmodel.MyViewModelFactory
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.adapter.ForecastAdapter
import com.ahoy.weatherapp.callbacks.CardSelectionListener
import com.ahoy.weatherapp.constants.Temprature
import com.ahoy.weatherapp.databinding.FragmentHomeBinding
import com.ahoy.weatherapp.repo.WeatherRepository
import com.ahoy.weatherapp.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment(), CardSelectionListener {

     private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var binding : FragmentHomeBinding
    var forecastAdapter : ForecastAdapter = ForecastAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate<FragmentHomeBinding>(layoutInflater,
            R.layout.fragment_home,container,false)

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        setupAdapter()
        //TODO: Refactor static key
        getUserLocation()

        viewModel.temprature.observe(viewLifecycleOwner, Observer {
            tvTemprature.text = it.getTemp()
        })

        viewModel.observeCurrentWeather().observe(viewLifecycleOwner, Observer {
            viewModel.weatherResponse.set(it)
            viewModel.temprature.value = Temprature(valueInKelvin = it.main.temp.roundToInt())
        })



    }

    private fun getUserLocation() {
        arguments?.getParcelable<Location>("location")?.let {
            updateLocationName(it)
            viewModel.fetchCurrentWeather(it)
            viewModel.fetchForecast(it)
        }
    }

    private fun updateLocationName(it: Location) {
        Geocoder(context).getFromLocation(it.latitude,
                it.longitude, 2)?.first()?.locality?.let {
            tvLocationName.text = it
        }
    }

    private fun setupAdapter() {
        rvWeatherForecast.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        forecastAdapter.addListener(this)
        rvWeatherForecast.adapter = forecastAdapter
        observeForecast()
    }

    private fun observeForecast() {
        viewModel.observeForecast().observe(viewLifecycleOwner, Observer {
            forecastAdapter.addItems(it)
        })
    }


    override fun onItemSelected(date: Long) {
        DetailFragment().apply {
            val args = Bundle()
            args.putLong("forecast_selected_item_date",date)
            arguments = args
            this@HomeFragment.activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, this).addToBackStack("")?.commit()
        }
    }

}