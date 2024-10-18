package com.omnitech.weatherapptest.domain.repository

import com.omnitech.weatherapptest.data.remote.dto.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherForecast(city: String): WeatherResponse
}
