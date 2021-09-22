package com.example.taskmanager.repository.retrofit.dto

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)