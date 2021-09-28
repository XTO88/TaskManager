package com.example.taskmanager.data.retrofit.dto

data class WeatherDto(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

fun WeatherDto.toWeather(): com.example.taskmanager.domain.model.Weather{
    return com.example.taskmanager.domain.model.Weather(
        city = name,
        temperature = main.temp.toInt(),
        icon = weather[0].icon
    )
}