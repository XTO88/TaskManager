package com.example.taskmanager.domain.use_case.task

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repository.FirestoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TaskOperationsUseCase @Inject constructor(
    private val firestore: FirestoreRepository,
    @ApplicationContext private val context: Context
) {

    fun getTask(id:String): Task?{
        return firestore.getTask(id)
    }

    suspend fun insertTask(t:Task){
        try{
            firestore.insertTask(t)
        } catch (ex:Exception) {
            Toast.makeText(context,ex.message?:"Firestore Error",LENGTH_LONG).show()
        }
    }

    suspend fun updateTask(t:Task) {
        try{
            firestore.updateTask(t)
        } catch (ex:Exception) {
            Toast.makeText(context,ex.message?:"Firestore Error",LENGTH_LONG).show()
        }
    }

    suspend fun deleteTask(t:Task){
        try{
            firestore.deleteTask(t)
        } catch (ex:Exception) {
            Toast.makeText(context,ex.message?:"Firestore Error",LENGTH_LONG).show()
        }
    }

    suspend fun completeTask(t:Task){
        try{
            firestore.completeTask(t)
        } catch (ex:Exception) {
            Toast.makeText(context,ex.message?:"Firestore Error",LENGTH_LONG).show()
        }
    }
}