package com.example.taskmanager.domain.repository

import com.example.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.StateFlow

interface FirestoreRepository {

    suspend fun insertTask(t: Task)
    suspend fun updateTask(t: Task)
    suspend fun completeTask(t: Task)
    suspend fun deleteTask(t: Task)

    fun getTask(id: String): Task?
    fun getTasks():StateFlow<List<Task>>
}