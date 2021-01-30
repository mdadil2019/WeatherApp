package com.ahoy.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ahoy.weatherapp.fragments.HomeFragment
import com.ahoy.weatherapp.fragments.LocationPermissionFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        changeFragment(LocationPermissionFragment())
    }

    fun changeFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }


}