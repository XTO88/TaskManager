package com.example.taskmanager.repository.retrofit

import android.content.res.Resources
import com.example.taskmanager.R
import com.example.taskmanager.model.Weather
import com.example.taskmanager.repository.retrofit.dto.toWeather
import javax.inject.Inject

class WeatherApiImpl @Inject constructor(
    private val api:WeatherApi
) {

    @Inject
    lateinit var resources:Resources

    suspend fun getWeather():Weather{
        return api.getWeather("Kyiv",
            resources.getString(R.string.weather_api_key),
            "metric").toWeather()
    }

}