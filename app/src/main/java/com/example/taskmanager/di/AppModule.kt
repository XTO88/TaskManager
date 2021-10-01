package com.example.taskmanager.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.taskmanager.common.Constants
import com.example.taskmanager.domain.repository.FirestoreRepository
import com.example.taskmanager.data.firestore.FirestoreRepositoryImpl
import com.example.taskmanager.data.retrofit.WeatherApi
import com.example.taskmanager.data.retrofit.WeatherRepositoryImpl
import com.example.taskmanager.domain.repository.WeatherRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor


@Module
class AppModule (
    private  val context: Context
        ){

    @Provides
    @Singleton
    fun provideResources():Resources{
        return context.resources
    }

    @Provides
    @Singleton
    fun provideFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreRepo(fireStore:FirebaseFirestore): FirestoreRepository {
        val repo = FirestoreRepositoryImpl(fireStore)
        repo.init()
        return repo
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(Constants.WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api:WeatherApi, resources: Resources):WeatherRepository{
        return WeatherRepositoryImpl(api,resources)
    }

    @Provides
    @Singleton
    fun provideGlide() :RequestManager{
        return Glide.with(context)
    }
}