package com.example.taskmanager.ui.taskScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.domain.use_case.task.TaskOperationsUseCase
import com.example.taskmanager.ui.homeScreen.HomeViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class TaskViewModelFactory @AssistedInject constructor(
    private val taskOperationsUseCase: TaskOperationsUseCase,
    @Assisted("taskKey")private val taskId:String
) :ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == TaskViewModel::class.java)
        return TaskViewModel(taskOperationsUseCase ,taskId) as T
    }

    @AssistedFactory
    interface Factory{
        fun create(@Assisted("taskKey") taskId: String):TaskViewModelFactory
    }

}