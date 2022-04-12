package com.example.places_with_weather_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.example.places_with_weather_app.ui.theme.resources.*
import com.example.places_with_weather_app.ui.theme.resources.LocalAppDimens
import com.example.places_with_weather_app.ui.theme.resources.LocalAppShapes
import com.example.places_with_weather_app.ui.theme.resources.primaryDark

private val DarkColorPalette = darkColors(
    primary = primaryDark,
    primaryVariant = primaryVariant,
    secondary = secondary
)

private val LightColorPalette = lightColors(
    primary = primaryLight,
    primaryVariant = primaryVariant,
    secondary = secondary,

    /* Other default colors to override
    background = Color.Yellow,
    surface = Color.Red,
    onPrimary = Color.Green,
    onSecondary = Color.Blue,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AppBaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

object AppBaseTheme {

    val dimens: AppDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimens.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

}