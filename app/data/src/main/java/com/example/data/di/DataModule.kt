package com.example.data.di

import com.example.data.datalayer.FavouritePlacesDao
import com.example.data.repositories.map.MapRepository
import com.example.data.repositories.map.MapRepositoryImpl
import com.example.data.repositories.weather.WeatherRepository
import com.example.data.repositories.weather.WeatherRepositoryImpl
import com.example.domain.common.KtorClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMapRepository(favouritePlacesDao: FavouritePlacesDao): MapRepository {
        return MapRepositoryImpl(favouritePlacesDao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(client: KtorClient, favouritePlacesDao: FavouritePlacesDao): WeatherRepository {
        return WeatherRepositoryImpl(client, favouritePlacesDao)
    }
}