package com.example.weatherapi.repository


import com.example.weatherapi.model.WeatherResponse
import com.example.weatherapi.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    suspend fun getWeatherByCity(city: String, apiKey: String = "099e1c6ae6fd737e6f509857ede00327"): Result<WeatherResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getWeatherByCity(city, apiKey)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}