package com.ahoy.weatherapp.locationmanager

import android.location.Location

interface LocationListener {
    fun onLocationFound(location : Location)
    fun onLocationNotFound(error : String?)
    fun onLocationPermissionNotGiven()
    fun onLocationNameFound(name : String)
}