package me.aluceps.practiceroom

import android.app.Application
import android.arch.persistence.room.Room

class App : Application() {

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        initializeDatabase()
    }

    private fun initializeDatabase() {
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "practice-room")
                .build()
    }
}