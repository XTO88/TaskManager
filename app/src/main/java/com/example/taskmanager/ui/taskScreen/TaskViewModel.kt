package com.example.taskmanager.ui.taskScreen

import androidx.lifecycle.*
import com.example.taskmanager.model.Task
import com.example.taskmanager.repository.firebase.FirestoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    dataSource: FirestoreRepositoryImpl,
    savedStateHandle: SavedStateHandle
) :ViewModel() {

    private val database = dataSource

    val task:Task? = dataSource.getTask(savedStateHandle["taskKey"]!!)

    private val _navigateToHome = MutableLiveData<Boolean?>()

    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    fun doneNavigating() {
        _navigateToHome.value = null
    }

    fun updateText(text:String){
        viewModelScope.launch {
            task?.let{
                task.text = text
                database.updateTask(task)
            }
        }
    }

    fun updateTime(time: Date){
        viewModelScope.launch {
            task?.let {
                task.time = time.time
                database.updateTask(task)
            }
        }
    }
}