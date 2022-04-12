package com.example.data.repositories.weather

import com.example.data.entities.ui.MapMarkerUi
import com.example.data.entities.ui.WeatherUi
import com.example.domain.common.Result

interface WeatherRepository {

    fun getWeather(lat: Double, lng: Double, callback: (Result<WeatherUi>) -> Unit)

    fun getPlace(lat: Double, lng: Double, callback: (Result<MapMarkerUi>) -> Unit)

    fun updatePlace(place: MapMarkerUi, callback: (Result<MapMarkerUi>) -> Unit)
}