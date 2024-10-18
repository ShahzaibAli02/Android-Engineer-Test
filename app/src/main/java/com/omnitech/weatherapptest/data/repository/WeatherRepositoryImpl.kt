package com.omnitech.weatherapptest.data.repository

import com.omnitech.weatherapptest.data.remote.api.WeatherApi
import com.omnitech.weatherapptest.data.remote.dto.WeatherResponse
import com.omnitech.weatherapptest.domain.repository.WeatherRepository
import retrofit2.Response

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val apiKey: String
) : WeatherRepository
{

    override suspend fun getWeatherForecast(city: String): WeatherResponse
    {
        return api.getWeatherForecast(
                city = city,
                apiKey = apiKey // Replace with your WeatherAPI key
        )
    }
}
