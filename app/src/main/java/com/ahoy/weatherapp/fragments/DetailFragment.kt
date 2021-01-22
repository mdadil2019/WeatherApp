package com.ahoy.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.viewmodel.MyViewModelFactory
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.databinding.FragmentDetailBinding
import com.ahoy.weatherapp.repo.WeatherRepository
import com.ahoy.weatherapp.viewmodel.DetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    lateinit var viewModel : DetailFragmentViewModel
    lateinit var binding : FragmentDetailBinding
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
                DetailFragmentViewModel(WeatherRepository(context!!, viewLifecycleOwner))
            }).get(DetailFragmentViewModel::class.java)

        binding.viewModel = viewModel
        rvDetailForecast.layoutManager = LinearLayoutManager(context!!,
            RecyclerView.VERTICAL,false)
        rvDetailForecast.adapter = viewModel.detailForecastAdapter

        arguments?.getLong("forecast_selected_item_date")?.let {
            viewModel.getForecastOfDay(it)
            viewModel.getForecast()
        }
    }
}