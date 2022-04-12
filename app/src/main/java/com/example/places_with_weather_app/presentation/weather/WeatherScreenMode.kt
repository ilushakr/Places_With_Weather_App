package com.example.places_with_weather_app.presentation.weather

sealed class WeatherScreenMode{
    object Edit: WeatherScreenMode()
    object Exist: WeatherScreenMode()
    object NotExist: WeatherScreenMode()
    object Pending: WeatherScreenMode()
    object Error: WeatherScreenMode()
}
