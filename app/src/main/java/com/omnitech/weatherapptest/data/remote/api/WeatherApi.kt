package com.omnitech.weatherapptest.data.remote.api

import com.omnitech.weatherapptest.data.remote.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface WeatherApi {
    @GET("v1/forecast.json")
    suspend fun getWeatherForecast(
        @Query("q") city: String,
        @Query("days") days: Int = 1, // Days for forecast
        @Query("key") apiKey: String
    ): WeatherResponse
}
