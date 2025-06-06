package com.omnitech.weatherapptest.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL for the OpenWeather API
    private const val BASE_URL = "https://api.weatherapi.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}
