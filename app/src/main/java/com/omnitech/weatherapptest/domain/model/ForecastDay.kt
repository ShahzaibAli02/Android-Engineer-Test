package com.omnitech.weatherapptest.domain.model
data class ForecastDay(
    val date: String,
    val hour:List<Hour>,
)
data class Hour(
        val time:String,
        val temp_c:Double
)
