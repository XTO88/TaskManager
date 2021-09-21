package com.example.taskmanager

import com.example.taskmanager.database.FirestoreRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreRepo(fireStore:FirebaseFirestore):FirestoreRepositoryImpl{
        val repo = FirestoreRepositoryImpl(fireStore)
        repo.init()
        return repo
    }
}