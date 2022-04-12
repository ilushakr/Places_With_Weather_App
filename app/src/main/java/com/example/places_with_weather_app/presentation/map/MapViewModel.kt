package com.example.places_with_weather_app.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entities.ui.MapMarkerUi
import com.example.data.repositories.map.MapRepository
import com.example.domain.common.Result
import com.example.places_with_weather_app.navigation.Screens
import com.example.places_with_weather_app.presentation.utils.UiEvent
import com.example.places_with_weather_app.presentation.weather.WeatherArgs
import com.example.places_with_weather_app.utils.GsonUtils
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(private val repository: MapRepository) : ViewModel() {

    private val _markersState = MutableStateFlow<List<MapMarkerUi>>(emptyList())
    val markerState: StateFlow<List<MapMarkerUi>> = _markersState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getPlaces() {
        repository.getFavouritePlaces { result ->
            when (result) {
                is Result.Success -> _markersState.value = result.data
                is Result.Fail -> Unit
                is Result.Pending -> Unit
            }
        }
    }

    fun onEvent(mapScreenEvent: MapScreenEvent) {
        when (mapScreenEvent) {
            is MapScreenEvent.DeleteMarker -> {
                repository.deletePlace(
                    MapMarkerUi(
                        latLng = mapScreenEvent.marker.position,
                        title = mapScreenEvent.marker.title ?: mapScreenEvent.marker.position.toString()
                    )
                ) { result ->
                    when (result) {
                        is Result.Success -> {
                            _markersState.value = result.data
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    message = "Marker was successfully deleted"
                                )
                            )
                        }
                        is Result.Fail -> {
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    message = mapScreenEvent.errorMessage
                                )
                            )
                        }
                        is Result.Pending -> Unit
                    }
                }
            }
            is MapScreenEvent.CreateMarker -> {
                repository.saveFavouritePlace(
                    MapMarkerUi(
                        latLng = mapScreenEvent.latLng,
                        title = mapScreenEvent.latLng.toString()
                    )
                ) { result ->
                    when (result) {
                        is Result.Success -> _markersState.value = result.data
                        is Result.Fail -> {
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    message = mapScreenEvent.errorMessage
                                )
                            )
                        }
                        is Result.Pending -> Unit
                    }
                }
            }
            is MapScreenEvent.OpenWeatherScreen -> {
                mapScreenEvent.location?.let { latLng ->
                    val route = Screens.WeatherScreen.withArgs(
                        GsonUtils.toArgs(
                            WeatherArgs(
                                latLng.latitude,
                                latLng.longitude
                            )
                        )
                    )
                    sendUiEvent(
                        UiEvent.Navigate(route = route)
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}