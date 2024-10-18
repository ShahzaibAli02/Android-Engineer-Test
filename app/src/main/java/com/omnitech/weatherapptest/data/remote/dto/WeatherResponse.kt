package com.omnitech.weatherapptest.data.remote.dto

import com.omnitech.weatherapptest.domain.model.CurrentWeather
import com.omnitech.weatherapptest.domain.model.Forecast

data class WeatherResponse(
    val error: Error?,
    val current: CurrentWeather?,
    val forecast: Forecast?
)

data class Error(
    val code:Int,
        val message:String
)