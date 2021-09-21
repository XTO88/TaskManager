package com.example.taskmanager.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val fireStore:FirebaseFirestore
):FirestoreRepository{

    var tasks : MutableLiveData<List<Task>> = MutableLiveData()

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

    override fun getTask(id: String): Task?{
        return tasks.value?.single { it.id == id }
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