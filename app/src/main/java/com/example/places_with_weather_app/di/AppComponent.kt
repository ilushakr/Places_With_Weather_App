package com.example.places_with_weather_app.di

import androidx.compose.material.ExperimentalMaterialApi
import com.example.data.di.DataModule
import com.example.data.di.RoomModule
import com.example.places_with_weather_app.presentation.map.MapViewModel
import com.example.places_with_weather_app.presentation.weather.WeatherViewModel
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@ExperimentalMaterialApi
@Singleton
@Component(modules = [DataModule::class, RoomModule::class, ApiModule::class, AppModule::class])
interface AppComponent {

    fun getMapViewModel(): MapViewModel

    fun getWeatherViewModel(): WeatherViewModel
}