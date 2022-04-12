package com.example.data.repositories.map

import com.example.data.datalayer.FavouritePlacesDao
import com.example.data.entities.room.MapMarkerRoom
import com.example.data.entities.ui.MapMarkerUi
import com.example.domain.common.Result
import com.example.domain.providers.AbstractCoroutineProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(val favouritePlacesDao: FavouritePlacesDao) : AbstractCoroutineProvider(), MapRepository {

    override fun getFavouritePlaces(callback: (Result<List<MapMarkerUi>>) -> Unit) {
        scope.launch {
            val resultList = favouritePlacesDao.getFavouritePlaces()
            withContext(Dispatchers.Main) {
                callback(Result.Success(data = resultList.map { it.mapUi() }))
            }
        }
    }

    override fun saveFavouritePlace(place: MapMarkerUi, callback: (Result<List<MapMarkerUi>>) -> Unit) {
        scope.launch {
            val result = try {
                favouritePlacesDao.insertFavouritePlace(
                    MapMarkerRoom(
                        lat = place.latLng.latitude,
                        lng = place.latLng.longitude,
                        title = place.title
                    )
                )
                Result.Success(favouritePlacesDao.getFavouritePlaces().map { it.mapUi() })
            } catch (e: Throwable) {
                Result.Fail(e)
            }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun deletePlace(place: MapMarkerUi, callback: (Result<List<MapMarkerUi>>) -> Unit) {
        scope.launch {
            val result = try {
                favouritePlacesDao.deleteFavouritePlace(
                    MapMarkerRoom(
                        lat = place.latLng.latitude,
                        lng = place.latLng.longitude,
                        title = place.title
                    )
                )
                Result.Success(favouritePlacesDao.getFavouritePlaces().map { it.mapUi() })
            } catch (e: Throwable) {
                Result.Fail(e)
            }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }


}