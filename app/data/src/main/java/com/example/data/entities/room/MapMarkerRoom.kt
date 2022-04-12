package com.example.data.entities.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.data.entities.ui.MapMarkerUi
import com.example.domain.entities.AbstractResult
import com.google.android.gms.maps.model.LatLng

@Entity(primaryKeys = ["lat", "lng"])
data class MapMarkerRoom(
    val lat: Double,
    val lng: Double,
    @ColumnInfo(name = "title") val title: String
) :
    AbstractResult<MapMarkerUi>() {

    override fun mapUi(): MapMarkerUi {
        return MapMarkerUi(
            latLng = LatLng(lat, lng),
            title = title
        )
    }

}
