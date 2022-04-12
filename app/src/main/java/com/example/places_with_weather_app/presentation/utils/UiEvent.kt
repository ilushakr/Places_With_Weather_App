package com.example.places_with_weather_app.presentation.utils

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackbar(val message: String, val action: String? = null) : UiEvent()
}