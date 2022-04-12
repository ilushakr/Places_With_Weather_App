package com.example.data.repositories.weather

import com.example.data.BuildConfig
import com.example.data.datalayer.FavouritePlacesDao
import com.example.domain.common.Result
import com.example.data.endPoints.EndPoints
import com.example.data.entities.ktor.WeatherKtor
import com.example.data.entities.room.MapMarkerRoom
import com.example.data.entities.room.NotExistingPlaceError
import com.example.data.entities.ui.MapMarkerUi
import com.example.data.entities.ui.WeatherUi
import com.example.domain.common.KtorClient
import com.example.domain.providers.AbstractCoroutineProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(private val client: KtorClient, private val favouritePlacesDao: FavouritePlacesDao) : AbstractCoroutineProvider(),
    WeatherRepository {

    override fun getWeather(lat: Double, lng: Double, callback: (Result<WeatherUi>) -> Unit) {
        execute<WeatherUi, WeatherKtor>(callback) {
            client.get(
                url = EndPoints.WEATHER_URL,
                queryParams = mapOf(
                    "lat" to lat.toString(),
                    "lon" to lng.toString(),
                    "lang" to "ru",
                    "units" to "metric",
                    "appid" to BuildConfig.WEATHER_KEY
                )
            )
        }
    }

    override fun updatePlace(place: MapMarkerUi, callback: (Result<MapMarkerUi>) -> Unit) {
        scope.launch {
            val result = try {
                favouritePlacesDao.insertFavouritePlace(
                    MapMarkerRoom(
                        lat = place.latLng.latitude,
                        lng = place.latLng.longitude,
                        title = place.title
                    )
                )
                val updatedPlace = favouritePlacesDao.getPlace(place.latLng.latitude, place.latLng.longitude)
                if (updatedPlace != null) {
                    Result.Success(updatedPlace.mapUi())
                } else {
                    Result.Fail(NotExistingPlaceError)
                }

            } catch (e: Throwable) {
                Result.Fail(e)
            }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun getPlace(lat: Double, lng: Double, callback: (Result<MapMarkerUi>) -> Unit) {
        execute(callback) {
            try {
                val place = favouritePlacesDao.getPlace(lat, lng)
                if (place != null) {
                    Result.Success(place)
                } else {
                    Result.Fail(NotExistingPlaceError)
                }
            } catch (e: Throwable) {
                Result.Fail(e)
            }
        }
    }
}