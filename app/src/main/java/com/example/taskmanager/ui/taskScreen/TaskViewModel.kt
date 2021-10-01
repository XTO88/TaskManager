package com.example.taskmanager.ui.taskScreen

import androidx.lifecycle.*
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.use_case.task.TaskOperationsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

class TaskViewModel (
    private val taskOperationsUseCase: TaskOperationsUseCase,
    taskId: String
) :ViewModel() {

    val task:Task? = taskOperationsUseCase.getTask(taskId)

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean>
        get() = _navigateToHome

    fun doneNavigating() {
        _navigateToHome.value = false
    }

    fun updateText(text:String){
        viewModelScope.launch {
            task?.let{
                task.text = text
                taskOperationsUseCase.updateTask(task)
            }
        }
    }

    fun updateTime(time: Date){
        viewModelScope.launch {
            task?.let {
                task.time = time.time
                taskOperationsUseCase.updateTask(task)
            }
        }
    }
}