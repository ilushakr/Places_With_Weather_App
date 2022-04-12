package com.example.places_with_weather_app.presentation.map

import com.google.android.gms.maps.model.Marker

sealed class BottomSheetType {
    data class MarkerBottomSheet(val marker: Marker) : BottomSheetType()
    object MapBottomSheet : BottomSheetType()
}
