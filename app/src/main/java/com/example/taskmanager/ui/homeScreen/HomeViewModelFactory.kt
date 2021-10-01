package com.example.taskmanager.ui.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.taskmanager.domain.use_case.task.GetTasksUseCase
import com.example.taskmanager.domain.use_case.task.TaskOperationsUseCase
import com.example.taskmanager.domain.use_case.weather.GetWeatherUseCase
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val weatherUseCase: GetWeatherUseCase,
    private val taskOperationsUseCase: TaskOperationsUseCase,
    val glide: RequestManager
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == HomeViewModel::class.java)
        return HomeViewModel(getTasksUseCase,weatherUseCase, taskOperationsUseCase, glide) as T
    }

}