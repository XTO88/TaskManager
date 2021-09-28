package com.example.taskmanager.data.retrofit

import com.example.taskmanager.data.retrofit.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") appKey: String,
        @Query("units") unit: String
    ): WeatherDto
}