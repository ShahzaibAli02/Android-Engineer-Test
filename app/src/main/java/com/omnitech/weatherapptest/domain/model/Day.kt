package com.omnitech.weatherapptest.domain.model

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val condition: Condition
)