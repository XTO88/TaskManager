package com.example.taskmanager.domain.use_case.task

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repository.FirestoreRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repo:FirestoreRepository
){
    operator fun invoke():StateFlow<List<Task>>{
        return repo.getTasks()
    }
}