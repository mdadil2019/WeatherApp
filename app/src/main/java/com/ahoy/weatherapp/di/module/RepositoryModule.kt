package com.ahoy.weatherapp.di.module

import android.content.Context
import com.ahoy.weatherapp.repo.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideWeatherRepository(@ApplicationContext context: Context) : WeatherRepository{
        return WeatherRepository(context)
    }

}