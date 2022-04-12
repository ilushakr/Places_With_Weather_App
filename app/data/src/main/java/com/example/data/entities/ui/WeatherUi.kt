package com.example.data.entities.ui

import com.google.android.gms.maps.model.LatLng

data class WeatherUi(
    val location: LatLng,
    val weatherDescription: List<WeatherDescriptionUi>,
    val base: String,
    val characteristics: MainWeatherUi,
    val visibility: Long,
    val wind: WindUi,
    val clouds: CloudsUi,
    val date: Long,
    val systemData: SystemWeatherUi,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)


data class CloudsUi(
    val all: Long
)

data class CoordinatesUi(
    val lon: Double,
    val lat: Double
)

data class MainWeatherUi(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Long,
    val humidity: Long
)

data class SystemWeatherUi(
    val type: Long?,
    val id: Long?,
    val message: Double?,
    val country: String?,
    val sunrise: Long,
    val sunset: Long
)

data class WeatherDescriptionUi(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class WindUi(
    val speed: Double,
    val deg: Long
)

