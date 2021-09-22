package com.example.taskmanager.ui.homeScreen

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.example.taskmanager.model.Task
import com.example.taskmanager.model.Weather
import com.example.taskmanager.repository.firebase.FirestoreRepositoryImpl
import com.example.taskmanager.repository.retrofit.WeatherApiImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fireStoreRepo: FirestoreRepositoryImpl,
    private val weatherApi: WeatherApiImpl,
    val glide:RequestManager
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

    private val _weather = MutableLiveData<Weather?>()
    val weather : LiveData<Weather?>
    get() = _weather

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress : LiveData<Boolean>
    get() = _showProgress

    val tasks = fireStoreRepo.tasks

    init {
        viewModelScope.launch {
            _showProgress.value = true
            try {
                _weather.value = weatherApi.getWeather()
                _showProgress.value = false
            } catch (e: IOException){
                Log.e(TAG, e.message?:"IOException" )
                _showProgress.value = false
                return@launch
            } catch (e:HttpException){
                Log.e(TAG,e.message?:"HttpException")
                _showProgress.value = false
                return@launch
            } catch (e:NullPointerException){
                Log.e(TAG,e.message?:"NullPointerException")
                e.printStackTrace()
                _showProgress.value = false
                return@launch
            }
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
                fireStoreRepo.insertTask(Task(newTask.value!!))
                _newTask.value = ""
                _closeKeyboard.value = true
            }
        }
    }

    fun completeTask(t:Task){
        viewModelScope.launch {
            fireStoreRepo.completeTask(t)
        }
    }
}