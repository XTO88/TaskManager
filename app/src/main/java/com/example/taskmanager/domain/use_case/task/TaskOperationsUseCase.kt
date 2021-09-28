package com.example.taskmanager.domain.use_case.task

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repository.FirestoreRepository
import javax.inject.Inject

class TaskOperationsUseCase @Inject constructor(
    private val firestore: FirestoreRepository
) {

    fun getTask(id:String): Task?{
        return firestore.getTask(id)
    }

    suspend fun insertTask(t:Task){
        firestore.insertTask(t)
    }

    suspend fun updateTask(t:Task){
        firestore.updateTask(t)
    }

    suspend fun deleteTask(t:Task){
        firestore.deleteTask(t)
    }

    suspend fun completeTask(t:Task){
        firestore.completeTask(t)
    }
}