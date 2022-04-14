package com.example.places_with_weather_app.presentation.map

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

sealed class MapScreenEvent {
    data class CreateMarker(val latLng: LatLng) : MapScreenEvent()
    data class DeleteMarker(val marker: Marker) : MapScreenEvent()
    data class OpenWeatherScreen(val location: LatLng?) : MapScreenEvent()
}
