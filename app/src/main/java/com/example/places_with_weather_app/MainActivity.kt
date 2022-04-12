package com.example.places_with_weather_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.example.places_with_weather_app.navigation.Navigation
import com.example.places_with_weather_app.presentation.map.MapViewModel
import com.example.places_with_weather_app.ui.theme.AppBaseTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalComposeUiApi
@ExperimentalSerializationApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val t = viewModels<MapViewModel>()
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.LightGray,
                    darkIcons = useDarkIcons
                )
            }

            AppBaseTheme {
                Navigation((application as App).appComponent)
            }
        }
    }
}