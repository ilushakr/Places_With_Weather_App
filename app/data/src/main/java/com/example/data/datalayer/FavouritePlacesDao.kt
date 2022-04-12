package com.example.data.datalayer

import androidx.room.*
import com.example.data.entities.room.MapMarkerRoom

@Dao
interface FavouritePlacesDao {

    @Query("SELECT * FROM MapMarkerRoom")
    suspend fun getFavouritePlaces(): List<MapMarkerRoom>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouritePlace(place: MapMarkerRoom)

    @Delete
    suspend fun deleteFavouritePlace(place: MapMarkerRoom)

    @Query("SELECT * FROM MapMarkerRoom WHERE lat =:lat AND lng =:lng")
    suspend fun getPlace(lat: Double, lng: Double): MapMarkerRoom?
}