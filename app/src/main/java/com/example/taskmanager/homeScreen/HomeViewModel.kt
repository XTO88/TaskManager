package com.example.taskmanager.homeScreen

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.database.Task
import com.example.taskmanager.database.FirestoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val database: FirestoreRepositoryImpl
) : ViewModel() {

    private val _navigateToTaskDetail = MutableLiveData<String?>()
    val navigateToTaskDetail: LiveData<String?>
        get() = _navigateToTaskDetail

    private val _closeKeyboard = MutableLiveData<Boolean>()
    val closeKeyboard : LiveData<Boolean>
    get() = _closeKeyboard

    private val _newTask = MutableLiveData<String?>()
    val newTask : LiveData<String?>
         get() = _newTask

    fun setNewTaskText(s: Editable){
        _newTask.value = s.toString()
    }

    val tasks = database.tasks

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
                database.insertTask(Task(newTask.value!!))
                _newTask.value = ""
                _closeKeyboard.value = true
            }
        }
    }

    fun completeTask(t:Task){
        viewModelScope.launch {
            database.completeTask(t)
        }
    }
}