package com.ahoy.weatherapp.fragments

import android.location.Geocoder
import android.location.Location
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
import com.ahoy.weatherapp.databinding.FragmentHomeBinding
import com.ahoy.weatherapp.repo.WeatherRepository
import com.ahoy.weatherapp.viewmodel.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var binding : FragmentHomeBinding
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
        viewModel = ViewModelProvider(this,
            MyViewModelFactory(
                HomeFragmentViewModel::class
            ) {
                HomeFragmentViewModel(WeatherRepository(context!!, viewLifecycleOwner))
            }).get(HomeFragmentViewModel::class.java)

        binding.viewModel = viewModel
        rvWeatherForecast.layoutManager = LinearLayoutManager(context!!,RecyclerView.HORIZONTAL,false)
        rvWeatherForecast.adapter = viewModel.forecastAdapter

        //TODO: Refactor static key
        arguments?.getParcelable<Location>("location")?.let {
            Geocoder(context!!).getFromLocation(it.latitude,
                it.longitude,2)?.first()?.locality?.let {
                tvLocationName.text = it
            }

            viewModel.fetchCurrentWeather(it)
            viewModel.fetchForecast(it)
        }

        viewModel.temprature.observe(viewLifecycleOwner, Observer {
            tvTemprature.text = it.getTemp()
        })

    }

}