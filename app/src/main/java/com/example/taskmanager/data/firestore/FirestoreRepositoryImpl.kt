package com.example.taskmanager.data.firestore

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FirestoreRepositoryImpl (
    private val fireStore:FirebaseFirestore
): FirestoreRepository {

    private val tasks = MutableStateFlow<List<Task>>(emptyList())

    fun init() {
        fireStore.collection("tasks").orderBy("completed").orderBy("time").
        addSnapshotListener { snapshot, _ ->
            val list = ArrayList<Task>()
            snapshot?.let {
                for (d in snapshot.documents) {
                    d.toObject(Task::class.java)?.let { it1 -> list.add(it1) }
                }
            }

            tasks.value = list
        }
    }

    override fun getTask(id: String): Task{
        return tasks.value.single { it.id == id }
    }

    override fun getTasks(): StateFlow<List<Task>> {
        return tasks
    }

    override suspend fun insertTask(t: Task) {
        fireStore.collection("tasks").add(t)
    }

    override suspend fun updateTask(t: Task) {
        fireStore.collection("tasks").document(t.id).set(t)
    }

    override suspend fun completeTask(t: Task) {
        fireStore.collection("tasks").document(t.id).update("completed",!t.completed)
    }

    override suspend fun deleteTask(t: Task) {
        fireStore.collection("tasks").document(t.id).delete()
    }
}