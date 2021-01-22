package com.ahoy.weatherapp.locationmanager

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import com.ahoy.weatherapp.locationmanager.LocationListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.ref.WeakReference

/*
This class should receive the location of the user and pass it to
subscribed class
 */
class LocationManager(private val context: Context) {

    private var listener = WeakReference<LocationListener>(null)
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var geoCoder = Geocoder(context)

    fun addListener(listener: LocationListener) {
        this.listener = WeakReference(listener)
    }


    fun getLocation() {
        if (!isPermissionGiven()) {
            listener.get()?.onLocationPermissionNotGiven()
        } else {
            getLastKnownLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            listener.get()?.onLocationFound(it)
            findLocationName(it)
        }.addOnFailureListener {
            listener.get()?.onLocationNotFound(it.message)
        }
    }

    private fun isPermissionGiven(): Boolean {
        return ((ActivityCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(context, permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED))
    }

    fun findLocationName(location : Location){
//        geoCoder.getFromLocation(location.latitude,location.longitude,2).apply {
//            listener.get()?.onLocationNameFound(this.first().locality)
//        }
    }

}