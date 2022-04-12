package com.example.places_with_weather_app

import android.app.Application
import androidx.compose.material.ExperimentalMaterialApi
import com.example.data.di.DataModule
import com.example.data.di.RoomModule
import com.example.places_with_weather_app.di.ApiModule
import com.example.places_with_weather_app.di.AppComponent
import com.example.places_with_weather_app.di.AppModule
import com.example.places_with_weather_app.di.DaggerAppComponent
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber

@ExperimentalSerializationApi
@ExperimentalMaterialApi
class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .dataModule(DataModule())
            .roomModule(RoomModule(this))
            .apiModule(ApiModule())
            .appModule(AppModule())
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}