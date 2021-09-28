package com.example.taskmanager.data.retrofit.dto

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)