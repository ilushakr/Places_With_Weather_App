package com.example.data.entities.ktor

import com.example.data.entities.ui.*
import com.example.domain.entities.AbstractResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherKtor(
    val coord: CoordinatesKtor,
    val weather: List<WeatherDescriptionKtor>,
    val base: String,
    val main: MainWeatherKtor,
    val visibility: Long,
    val wind: WindKtor,
    val clouds: CloudsKtor,
    val dt: Long,
    val sys: SystemWeatherKtor,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long

) : AbstractResult<WeatherUi>() {

    override fun mapUi(): WeatherUi {
        return WeatherUi(
            location = LatLng(coord.lat, coord.lon),
            weatherDescription = weather.map { it.mapUi() },
            base = base,
            characteristics = main.mapUi(),
            visibility = visibility,
            wind = wind.mapUi(),
            clouds = CloudsUi(clouds.all),
            date = dt,
            systemData = sys.mapUi(),
            timezone = timezone,
            id = id,
            name = name,
            cod = cod
        )
    }
}

@Serializable
data class CloudsKtor(
    val all: Long
)

@Serializable
data class CoordinatesKtor(
    val lon: Double,
    val lat: Double
)

@Serializable
data class MainWeatherKtor(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Long,
    val humidity: Long
) : AbstractResult<MainWeatherUi>() {
    override fun mapUi(): MainWeatherUi {
        return MainWeatherUi(
            temp,
            feelsLike,
            tempMin,
            tempMax,
            pressure,
            humidity
        )
    }
}

@Serializable
data class SystemWeatherKtor(
    val type: Long?,
    val id: Long?,
    val message: Double?,
    val country: String? = null,
    val sunrise: Long,
    val sunset: Long
) : AbstractResult<SystemWeatherUi>() {
    override fun mapUi(): SystemWeatherUi {
        return SystemWeatherUi(
            type,
            id,
            message,
            country,
            sunrise,
            sunset
        )
    }
}

@Serializable
data class WeatherDescriptionKtor(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) : AbstractResult<WeatherDescriptionUi>() {
    override fun mapUi(): WeatherDescriptionUi {
        return WeatherDescriptionUi(id, main, description, icon)
    }
}

@Serializable
data class WindKtor(
    val speed: Double,
    val deg: Long
) : AbstractResult<WindUi>() {
    override fun mapUi(): WindUi {
        return WindUi(speed, deg)
    }
}

