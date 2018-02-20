package me.aluceps.practiceroom

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}