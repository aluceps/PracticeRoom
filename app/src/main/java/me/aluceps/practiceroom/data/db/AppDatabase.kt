package me.aluceps.practiceroom.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import me.aluceps.practiceroom.data.db.dao.UserDao
import me.aluceps.practiceroom.data.db.entity.User

@Database(entities = [User::class], version = 7, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}