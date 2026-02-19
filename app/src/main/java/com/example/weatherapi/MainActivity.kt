package com.example.weatherapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapi.databinding.ActivityMainBinding
import com.example.weatherapi.viewModel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupViewModel()
        observeData()

        binding.submitButton.setOnClickListener {
            viewModel.fetchWeatherInfo(binding.editText.text.toString())
            binding.editText.text.clear()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {
        viewModel.weatherInfo.observe(this@MainActivity) { weatherInfo ->
            binding.cityName.text = weatherInfo.name
            binding.temperature.text = "Temp: ${weatherInfo.main.temp}"
            binding.description.text = "Desc: ${weatherInfo.weather[0].description}"
            binding.humidity.text = "Humidity: ${weatherInfo.main.humidity}"
            binding.windSpeed.text = "Wind Speed: ${weatherInfo.wind.speed}"
            Glide.with(this).load("https://www.openweathermap.org/img/wn/${weatherInfo.weather[0].icon}@2x.png").circleCrop().placeholder(R.mipmap.ic_launcher).into(binding.iconUrl)

        }

        viewModel.loading.observe(this) { isLoading ->
            Log.i("Loading", "observeData: $isLoading")
        }

        viewModel.error.observe(this) { error ->
            Log.i("Error", "observeData: $error")
            Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
        }


    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

}

