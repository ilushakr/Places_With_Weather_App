package com.example.data.endPoints

import io.ktor.http.*

object EndPoints {

    private const val BASE_PATH = "https://api.openweathermap.org"
    val BASE_URL = Url(BASE_PATH)
    val WEATHER_URL = Url("$BASE_PATH/data/2.5/weather")
}