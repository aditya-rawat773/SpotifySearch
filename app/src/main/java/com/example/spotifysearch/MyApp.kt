package com.example.spotifysearch

import android.app.Application
import androidx.room.Room
import com.example.spotifysearch.data.room.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(){
companion object {
    lateinit var database: AppDatabase
}

override fun onCreate() {
    super.onCreate()
    database = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).build()
}
}