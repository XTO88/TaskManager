package com.example.taskmanager.taskScreen

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.*
import com.example.taskmanager.database.Task
import com.example.taskmanager.database.FirestoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    dataSource:FirestoreRepositoryImpl,
    savedStateHandle: SavedStateHandle
) :ViewModel() {

    val database = dataSource

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