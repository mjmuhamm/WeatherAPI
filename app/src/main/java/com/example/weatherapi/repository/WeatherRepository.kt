package com.example.weatherapi.repository


import com.example.weatherapi.BuildConfig
import com.example.weatherapi.model.WeatherResponse
import com.example.weatherapi.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    val api = BuildConfig.WEATHER_API_KEY

    suspend fun getWeatherByCity(city: String, apiKey: String = api): Result<WeatherResponse> =
        withContext(Dispatchers.IO) {

            println("api key is $apiKey")
            try {
                val response = RetrofitClient.api.getWeatherByCity(city, apiKey)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}