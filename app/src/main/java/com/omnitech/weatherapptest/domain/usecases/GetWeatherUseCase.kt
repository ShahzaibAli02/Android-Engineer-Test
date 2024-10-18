package com.omnitech.weatherapptest.domain.usecases

import com.omnitech.weatherapptest.domain.repository.WeatherRepository
import com.omnitech.weatherapptest.common.Result
import com.omnitech.weatherapptest.data.remote.dto.WeatherResponse
import retrofit2.HttpException

class GetWeatherUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke(city: String): Result<WeatherResponse> {
        return try {
            val result=repository.getWeatherForecast(city)
            Result.Success(result)
        } catch (e: java.net.UnknownHostException) {
            Result.Error(Exception("No Internet Connection"))
        } catch (e: HttpException) {

            Result.Error(Exception("Result not found for $city"))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
