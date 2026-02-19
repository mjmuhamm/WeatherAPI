package com.example.weatherapi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapi.model.WeatherResponse
import com.example.weatherapi.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weatherInfo = MutableLiveData<WeatherResponse>()
    val weatherInfo: LiveData<WeatherResponse> = _weatherInfo

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchWeatherInfo(city: String) {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getWeatherByCity(city)
                .onSuccess { weatherInfo ->
                    _weatherInfo.value = weatherInfo
                    _loading.value = false
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Unknown error"
                    _loading.value = false
                }


        }
    }


}