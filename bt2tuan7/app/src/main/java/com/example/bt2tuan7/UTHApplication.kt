package com.example.bt2tuan7

import android.app.Application
import androidx.room.Room
import com.example.bt2tuan7.data.AppDatabase

class UTHApplication : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "uth_smart_tasks_db"
        ).build()
    }
}