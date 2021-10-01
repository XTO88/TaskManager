package com.example.taskmanager.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import com.example.taskmanager.MyApp
import com.example.taskmanager.data.retrofit.WeatherApi
import com.example.taskmanager.domain.repository.FirestoreRepository
import com.example.taskmanager.domain.repository.WeatherRepository
import com.example.taskmanager.ui.MainActivity
import com.example.taskmanager.ui.homeScreen.HomeFragment
import com.example.taskmanager.ui.taskScreen.TaskFragment
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: TaskFragment)

    val resources:Resources
    val firestore:FirebaseFirestore
    val fireStoreRepo:FirestoreRepository
    val weatherApi:WeatherApi
    val weatherRepository:WeatherRepository
    val requestManager:RequestManager
}
