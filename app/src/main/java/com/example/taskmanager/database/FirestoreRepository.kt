package com.example.taskmanager.database

interface FirestoreRepository {

    suspend fun insertTask(t:Task)
    suspend fun updateTask(t:Task)
    suspend fun completeTask(t:Task)
    suspend fun deleteTask(t:Task)

    fun getTask(id: String):Task?
}