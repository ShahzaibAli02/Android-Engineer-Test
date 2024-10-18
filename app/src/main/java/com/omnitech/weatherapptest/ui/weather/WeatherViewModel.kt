package com.omnitech.weatherapptest.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omnitech.weatherapptest.domain.usecases.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.omnitech.weatherapptest.common.Result
import com.omnitech.weatherapptest.common.Search
import com.omnitech.weatherapptest.data.remote.dto.WeatherResponse
import kotlinx.coroutines.flow.update

class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel()
{


    private val _weatherState = MutableStateFlow<Result<WeatherResponse>>(Result.None)
    val weatherState: StateFlow<Result<WeatherResponse>> = _weatherState


    private val _recentSearches = MutableStateFlow<MutableList<Search>>(mutableListOf())
    val recentSearches: StateFlow<List<Search>> = _recentSearches
    var searchQuery: String = ""
    fun clearRecentSearches()
    {
        _recentSearches.value = mutableListOf()
    }

    fun searchForWeather(city: String)
    {
        searchQuery = city
        if (recentSearches.value.none { it.content == city })
        { // Create a new search object
            val newSearch = Search(
                    city,
                    System.currentTimeMillis()
            )
            _recentSearches.value = recentSearches.value.toMutableList().apply {
                add(newSearch)
            }
        }
        getWeather(city)
    }

    private fun getWeather(city: String)
    {
        viewModelScope.launch {
            _weatherState.value = Result.Loading // Show loading state
            val result = getWeatherUseCase(city) // Call the use case
            _weatherState.value = result // Set the result (Success or Error)
        }
    }
}
