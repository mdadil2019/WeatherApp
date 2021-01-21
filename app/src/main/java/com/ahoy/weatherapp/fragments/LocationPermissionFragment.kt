package com.ahoy.weatherapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.ahoy.weatherapp.LocationListener
import com.ahoy.weatherapp.LocationManager
import com.ahoy.weatherapp.MainActivity
import com.ahoy.weatherapp.R
import kotlinx.android.synthetic.main.fragment_location_permission.*
import timber.log.Timber

class LocationPermissionFragment : Fragment(), LocationListener {
    var locationManager: LocationManager? = null

    companion object {
        private val LOCATION_PERMISSION_RC = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_permission, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        allowButton.setOnClickListener {
            initLocationManager()
        }

        if(isPermissionGiven()){
            initLocationManager()
        }
    }

    fun initLocationManager(){
        locationManager = LocationManager(context!!)
        locationManager?.addListener(this)
        locationManager?.getLocation()
    }

    private fun isPermissionGiven(): Boolean {
        return ((ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED))
    }

    override fun onLocationFound(location: Location) {
        Timber.e("onLocation Found $location")
        HomeFragment().apply {
            val bundle = Bundle()
            bundle.putParcelable("location",location)
            arguments = bundle
            changeFragment(this)
        }

    }



    override fun onLocationNotFound(error: String?) {
        Timber.e("location not found due to $error")
    }

    override fun onLocationPermissionNotGiven() {
        Timber.e("Location permission not given")
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            , LOCATION_PERMISSION_RC
        )
    }

    fun changeFragment(fragment : Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }

    override fun onLocationNameFound(name: String) {
        Toast.makeText(context!!,"Current location : $name", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_RC && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED
        ) {
            locationManager?.getLocation()
        } else {
            Toast.makeText(
                context!!,
                "You can't use the app without location permission",
                Toast.LENGTH_LONG
            ).show()
            locationManager = null
            activity!!.finish()
        }
    }


}