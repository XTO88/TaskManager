package com.example.taskmanager.data.firestore

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class FirestoreRepositoryImpl(
    fireStore: FirebaseFirestore
) : FirestoreRepository {

    private val tasks = MutableStateFlow<List<Task>>(emptyList())

    private val collection = fireStore.collection("tasks")

    fun init() {
        collection.orderBy("completed").orderBy("time").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Timber.e(error.message ?: "FirebaseError")
            } else {
                val list = ArrayList<Task>()
                snapshot?.let {
                    for (d in snapshot.documents) {
                        d.toObject(Task::class.java)?.let { it1 -> list.add(it1) }
                    }
                }
                tasks.value = list
            }
        }
    }

    override fun getTask(id: String): Task {
        return tasks.value.single { it.id == id }
    }

    override fun getTasks(): StateFlow<List<Task>> {
        return tasks
    }

    override suspend fun insertTask(t: Task) {
        collection.add(t).await()
    }

    override suspend fun updateTask(t: Task) {
        collection.document(t.id).set(t).await()
    }

    override suspend fun completeTask(t: Task) {
        collection.document(t.id).update("completed", !t.completed).await()
    }

    override suspend fun deleteTask(t: Task) {
        collection.document(t.id).delete().await()
    }

}