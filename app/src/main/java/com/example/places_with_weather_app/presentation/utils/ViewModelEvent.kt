package com.example.places_with_weather_app.presentation.utils

open class ViewModelEvent(
    open val message: String = "Success :)",
    open val errorMessage: String = "Oops! Something went wrong :(",
    open val action: String? = null
)