package com.example.data.datalayer

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entities.room.MapMarkerRoom

@Database(entities = [MapMarkerRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val favouritePlacesDao: FavouritePlacesDao

    companion object{
        const val NAME = "AppDatabase"
    }
}