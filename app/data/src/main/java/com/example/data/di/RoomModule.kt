package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.datalayer.AppDatabase
import com.example.data.datalayer.AppDatabase.Companion.NAME
import com.example.data.datalayer.FavouritePlacesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideChannelDao(appDatabase: AppDatabase): FavouritePlacesDao {
        return appDatabase.favouritePlacesDao
    }

}