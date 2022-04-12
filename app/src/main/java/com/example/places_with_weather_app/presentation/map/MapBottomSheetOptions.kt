package com.example.places_with_weather_app.presentation.map

import com.example.places_with_weather_app.wigets.BottomSheetOptions
import com.google.android.gms.maps.model.Marker

sealed class MapBottomSheetOptions(override val title: String) : BottomSheetOptions(title) {

    data class CreateMarker(override val title: String = "Create marker") :
        MapBottomSheetOptions(title)

    data class ShowWeather(override val title: String = "Show weather") :
        MapBottomSheetOptions(title)

    data class DeleteMarker(val marker: Marker, override val title: String = "Delete marker") :
        MapBottomSheetOptions(title)

}