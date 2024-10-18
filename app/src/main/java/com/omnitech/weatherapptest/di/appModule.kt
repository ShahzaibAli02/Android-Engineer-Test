package com.omnitech.weatherapptest.di

import com.omnitech.weatherapptest.data.remote.api.RetrofitInstance
import com.omnitech.weatherapptest.data.repository.WeatherRepositoryImpl
import com.omnitech.weatherapptest.domain.repository.WeatherRepository
import com.omnitech.weatherapptest.domain.usecases.GetWeatherUseCase
import com.omnitech.weatherapptest.ui.weather.WeatherViewModel
import com.omnitech.weatherapptest.utils.EncryptionUtil
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitInstance.api } // Provide Retrofit API
    single<WeatherRepository>  { WeatherRepositoryImpl(get(), EncryptionUtil.decrypt("mZXfrGomtQs0ZtlGpxPZRXPGYMX9rcuMzOphlEJOx1w","1234567890123456"))  }
    factory { GetWeatherUseCase(get()) }
    viewModel { WeatherViewModel(get()) }
}
