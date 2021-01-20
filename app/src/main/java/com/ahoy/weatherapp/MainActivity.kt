package com.ahoy.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import timber.log.Timber

class MainActivity : AppCompatActivity(), LocationListener {
    var locationManager: LocationManager? = null

    companion object {
        private val LOCATION_PERMISSION_RC = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        locationManager = LocationManager(this)
        locationManager?.addListener(this)
        locationManager?.getLocation()
    }

    override fun onLocationFound(location: Location) {
        Timber.e("onLocation Found $location")
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

    override fun onLocationNameFound(name: String) {
        Toast.makeText(this,"Current location : $name", Toast.LENGTH_LONG).show()
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
                this,
                "You can't use the app without location permission",
                Toast.LENGTH_LONG
            ).show()
            locationManager = null
            finish()
        }
    }


}