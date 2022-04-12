package com.example.places_with_weather_app.navigation

sealed class Screens(val route: String){
    object MapScreen: Screens(route = "map_screen")
    object WeatherScreen: Screens(route = "weather_screen")

    fun withArgs(vararg args: String) =
        buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
}
