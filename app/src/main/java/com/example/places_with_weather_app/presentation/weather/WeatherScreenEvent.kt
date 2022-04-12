package com.example.places_with_weather_app.presentation.weather

import com.example.places_with_weather_app.presentation.utils.ViewModelEvent

sealed class WeatherScreenEvent : ViewModelEvent() {
    data class ChangeMode(val mode: WeatherScreenMode) : WeatherScreenEvent()
    data class SavePlace(override val message: String, val emptyTitleMessage: String) : WeatherScreenEvent()
    object Cancel : WeatherScreenEvent()
    object OnBackClick : WeatherScreenEvent()
}
