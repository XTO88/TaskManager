package com.example.taskmanager.domain.use_case.weather

import com.example.taskmanager.common.Resource
import com.example.taskmanager.data.retrofit.dto.toWeather
import com.example.taskmanager.domain.model.Weather
import com.example.taskmanager.domain.repository.WeatherRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.isActive
import kotlinx.coroutines.isActive
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repo:WeatherRepository
){
    operator fun invoke(city:String): Flow<Resource<Weather>> = flow {
        while (currentCoroutineContext().isActive) {
            try {
                emit(Resource.Loading<Weather>())
                val weather = repo.getWeather(city).toWeather()
                emit(Resource.Success<Weather>(weather))
            } catch (e: HttpException) {
                emit(Resource.Error<Weather>(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: IOException) {
                emit(Resource.Error<Weather>("Couldn't reach server. Check your internet connection."))
            }
            kotlinx.coroutines.delay(5*60*1000L)
        }
    }
}