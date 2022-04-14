package com.example.places_with_weather_app.presentation.weather

sealed class WeatherScreenEvent {
    data class ChangeMode(val mode: WeatherScreenMode) : WeatherScreenEvent()
    object SavePlace : WeatherScreenEvent()
    object Cancel : WeatherScreenEvent()
    object OnBackClick : WeatherScreenEvent()
}
