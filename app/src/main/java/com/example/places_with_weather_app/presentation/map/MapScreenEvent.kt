package com.example.places_with_weather_app.presentation.map

import com.example.places_with_weather_app.presentation.utils.ViewModelEvent
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

sealed class MapScreenEvent : ViewModelEvent() {
    data class CreateMarker(val latLng: LatLng) : MapScreenEvent()
    data class DeleteMarker(val marker: Marker, override val message: String) : MapScreenEvent()
    data class OpenWeatherScreen(val location: LatLng?) : MapScreenEvent()
}
