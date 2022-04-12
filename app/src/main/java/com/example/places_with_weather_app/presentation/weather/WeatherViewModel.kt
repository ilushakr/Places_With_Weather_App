package com.example.places_with_weather_app.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entities.room.NotExistingPlaceError
import com.example.data.entities.ui.MapMarkerUi
import com.example.data.entities.ui.WeatherUi
import com.example.data.repositories.weather.WeatherRepository
import com.example.domain.common.Result
import com.example.places_with_weather_app.presentation.utils.UiEvent
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _placeState = MutableStateFlow<Result<MapMarkerUi>>(Result.Pending())

    private var tempPlace: MapMarkerUi? = null

    private val _modeState = MutableStateFlow<WeatherScreenMode>(WeatherScreenMode.Pending)
    val modeState: StateFlow<WeatherScreenMode> = _modeState.asStateFlow()

    private val _weatherState = MutableStateFlow<Result<List<String>>>(Result.Pending())
    val weatherState: StateFlow<Result<List<String>>> = _weatherState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getData(lat: Double, lng: Double) {

        repository.getPlace(lat, lng) {
            mapPlaceResult(it, lat, lng)
        }

        repository.getWeather(lat, lng) {
            _weatherState.value = when (it) {
                is Result.Fail -> Result.Fail(it.error)
                is Result.Pending -> Result.Pending()
                is Result.Success -> Result.Success(createWeatherInfoList(it.data))
            }
        }

    }

    val title: String
        get() = (_placeState.value as Result.Success<MapMarkerUi>).data.title

    fun updateTitle(title: String) {
        tempPlace = tempPlace?.copy(title = title)
    }

    fun onEvent(event: WeatherScreenEvent) {
        when (event) {
            is WeatherScreenEvent.ChangeMode -> {
                _modeState.value = event.mode
            }
            is WeatherScreenEvent.SavePlace -> {
                tempPlace?.let { tempPlaceNotNull ->
                    if (tempPlaceNotNull.title.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = event.emptyTitleMessage,
                            )
                        )
                        return
                    }
                    repository.updatePlace(tempPlaceNotNull) { result ->
                        mapPlaceResult(result, tempPlaceNotNull.latLng.latitude, tempPlaceNotNull.latLng.longitude)
                        val message = when (_modeState.value) {
                            WeatherScreenMode.Error -> event.errorMessage
                            WeatherScreenMode.Exist -> event.message
                            else -> null
                        }

                        message?.let {
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    message = it,
                                )
                            )
                        }
                    }
                }
            }
            is WeatherScreenEvent.Cancel -> {
                if (title.isEmpty()) _modeState.value = WeatherScreenMode.NotExist
                else _modeState.value = WeatherScreenMode.Exist
            }
            WeatherScreenEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun mapPlaceResult(result: Result<MapMarkerUi>, lat: Double, lng: Double) {
        _placeState.value = result
        when (result) {
            is Result.Fail -> {
                when (result.error) {
                    is NotExistingPlaceError -> {
                        _modeState.value = WeatherScreenMode.NotExist
                        _placeState.value = Result.Success(data = MapMarkerUi(latLng = LatLng(lat, lng), title = ""))
                        tempPlace = MapMarkerUi(latLng = LatLng(lat, lng), title = "")
                    }
                    else -> _modeState.value = WeatherScreenMode.Error
                }
            }
            is Result.Pending -> _modeState.value = WeatherScreenMode.Pending
            is Result.Success -> {
                tempPlace = MapMarkerUi(latLng = LatLng(lat, lng), title = result.data.title)
                _modeState.value = WeatherScreenMode.Exist
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun createWeatherInfoList(weather: WeatherUi) =
        mutableListOf<String>().apply {
            add("Temperature: ${weather.characteristics.temp}  \u2103")
            add("Feels like: ${weather.characteristics.feelsLike}  \u2103")
            add("Humidity: ${weather.characteristics.humidity}  %")
            add("Description: ${weather.weatherDescription.first().description}")
            add("Wind speed: ${weather.wind.speed}  m/s")
            add("City: ${weather.name}")
            weather.systemData.country?.let { country ->
                add("Country: ${Locale("", country)}")
            }
        }
}

