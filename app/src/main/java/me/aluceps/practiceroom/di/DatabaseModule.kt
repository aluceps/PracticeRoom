package me.aluceps.practiceroom.di

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import me.aluceps.practiceroom.data.db.AppDatabase
import me.aluceps.practiceroom.data.db.UserDatabase
import me.aluceps.practiceroom.data.db.UserRoomDatabase
import me.aluceps.practiceroom.data.db.dao.UserDao
import javax.inject.Singleton

@Module
open class DatabaseModule {

    companion object {
        val instance = DatabaseModule()
    }

    @Singleton
    @Provides
    open fun provideDb(app: Application): AppDatabase =
            Room.databaseBuilder(app, AppDatabase::class.java, "practice-room.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Singleton
    @Provides
    fun providesUserDao(db: AppDatabase): UserDao = db.userDao()

    @Singleton
    @Provides
    fun provideUserDatabase(appDatabase: AppDatabase, userDao: UserDao): UserDatabase =
            UserRoomDatabase(appDatabase, userDao)
}