package com.example.taskmanager

import android.app.Application
import android.content.Context
import com.example.taskmanager.di.AppComponent
import com.example.taskmanager.di.AppModule
import com.example.taskmanager.di.DaggerAppComponent

class MyApp:Application(){
    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MyApp -> appComponent
        else -> this.applicationContext.appComponent
    }