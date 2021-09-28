package com.example.taskmanager.domain.repository

import com.example.taskmanager.data.retrofit.dto.WeatherDto

interface WeatherRepository {

    suspend fun getWeather(city:String):WeatherDto
}