package com.example.places_with_weather_app.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.places_with_weather_app.di.AppComponent
import com.example.places_with_weather_app.presentation.map.MapScreen
import com.example.places_with_weather_app.presentation.utils.UiEvent
import com.example.places_with_weather_app.presentation.weather.WeatherInfoScreen
import com.example.places_with_weather_app.utils.daggerViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalComposeUiApi
@ExperimentalSerializationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(component: AppComponent) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MapScreen.route) {
        composable(route = Screens.MapScreen.route) {
            MapScreen(
                viewModel = daggerViewModel {
                    component.getMapViewModel()
                },
                onNavigate = { event ->
                    when (event) {
                        is UiEvent.Navigate -> navController.navigate(event.route)
                        else -> Unit
                    }
                }
            )
        }
        composable(
            route = Screens.WeatherScreen.route + "/{weatherArgs}",
            arguments = listOf(
                navArgument("weatherArgs") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString("weatherArgs")?.let { args ->
                WeatherInfoScreen(
                    viewModel = daggerViewModel {
                        component.getWeatherViewModel()
                    },
                    weatherArgs = args,
                    onNavigate = { event ->
                        when (event) {
                            is UiEvent.PopBackStack -> navController.popBackStack()
                            else -> Unit
                        }
                    }
                )
            }
        }
    }
}