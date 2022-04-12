package com.example.data.repositories.map

import com.example.data.entities.ui.MapMarkerUi
import com.example.domain.common.Result

interface MapRepository {

    fun getFavouritePlaces(callback: (Result<List<MapMarkerUi>>) -> Unit)

    fun saveFavouritePlace(place: MapMarkerUi, callback: (Result<List<MapMarkerUi>>) -> Unit)

    fun deletePlace(place: MapMarkerUi, callback: (Result<List<MapMarkerUi>>) -> Unit)
}