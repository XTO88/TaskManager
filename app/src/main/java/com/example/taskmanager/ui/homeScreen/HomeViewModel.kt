package com.example.taskmanager.ui.homeScreen

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.example.taskmanager.common.Resource
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.model.Weather
import com.example.taskmanager.domain.repository.FirestoreRepository
import com.example.taskmanager.domain.use_case.task.GetTasksUseCase
import com.example.taskmanager.domain.use_case.task.TaskOperationsUseCase
import com.example.taskmanager.domain.use_case.weather.GetWeatherUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val TAG = "HomeViewModel"

class HomeViewModel (
    getTasksUseCase: GetTasksUseCase,
    private val weatherUseCase: GetWeatherUseCase,
    private val taskOperationsUseCase: TaskOperationsUseCase,
    val glide:RequestManager
) : ViewModel() {



    private val _navigateToTaskDetail = MutableStateFlow<String?>(null)
    val navigateToTaskDetail: StateFlow<String?>
        get() = _navigateToTaskDetail.asStateFlow()

    private val _closeKeyboard = MutableStateFlow(false)
    val closeKeyboard : StateFlow<Boolean>
    get() = _closeKeyboard.asStateFlow()

    private val _newTask = MutableStateFlow<String?>("")
    val newTask : StateFlow<String?>
         get() = _newTask.asStateFlow()

    val tasks = getTasksUseCase()

    private lateinit var _weather:StateFlow<Resource<Weather>>
    val weather: StateFlow<Resource<Weather>>
    get() = _weather

    init {
        viewModelScope.launch {
            _weather = weatherUseCase("Kyiv").stateIn(viewModelScope)
        }
    }

    fun setNewTaskText(s: Editable){
        _newTask.value = s.toString()
    }

    fun doneNavigating() {
        _navigateToTaskDetail.value = null
    }

    fun keyboardClosed(){
        _closeKeyboard.value = false
    }

    fun openTask(id:String){
        _navigateToTaskDetail.value = id
    }

    fun onAddNewTask(){
        viewModelScope.launch {
            if(!newTask.value.isNullOrEmpty()){
                taskOperationsUseCase.insertTask(Task(newTask.value!!))
                _newTask.value = ""
                _closeKeyboard.value = true
            }
        }
    }

    fun completeTask(t:Task){
        viewModelScope.launch {
            taskOperationsUseCase.completeTask(t)
        }
    }
}