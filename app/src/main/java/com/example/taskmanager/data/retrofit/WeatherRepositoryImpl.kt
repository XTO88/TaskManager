package com.example.taskmanager.data.retrofit

import android.content.res.Resources
import com.example.taskmanager.R
import com.example.taskmanager.data.retrofit.dto.WeatherDto
import com.example.taskmanager.domain.model.Weather
import com.example.taskmanager.data.retrofit.dto.toWeather
import com.example.taskmanager.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api:WeatherApi,
    private val resources: Resources
) : WeatherRepository{

     override suspend fun getWeather(city: String):WeatherDto{
        return api.getWeather(city,
            resources.getString(R.string.weather_api_key),
            "metric")
    }

}