package com.example.places_with_weather_app.utils

import com.google.gson.Gson

object GsonUtils {

    fun <T> toArgs(argsClass: T): String = Gson().toJson(argsClass)

    inline fun <reified T> fromArgs(args: String): T = Gson().fromJson(args, T::class.java)
}