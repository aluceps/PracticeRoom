package me.aluceps.practiceroom.di

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import dagger.Module
import dagger.Provides
import me.aluceps.practiceroom.data.db.AppDatabase
import me.aluceps.practiceroom.data.db.UserDatabase
import me.aluceps.practiceroom.data.db.UserRoomDatabase
import me.aluceps.practiceroom.data.db.dao.UserDao
import javax.inject.Singleton

@Module
open class DatabaseModule {

    @Singleton
    @Provides
    open fun provideDb(app: Application): AppDatabase =
            Room.databaseBuilder(app, AppDatabase::class.java, "practice-room.db")
                    .addMigrations(migration)
                    .build()

    @Singleton
    @Provides
    fun providesUserDao(db: AppDatabase): UserDao = db.userDao()

    @Singleton
    @Provides
    fun provideUserDatabase(appDatabase: AppDatabase, userDao: UserDao): UserDatabase =
            UserRoomDatabase(appDatabase, userDao)

    companion object {
        val instance = DatabaseModule()

        const val user = "User"
        const val columnUid = "uid"
        const val columnFirst = "first_name"
        const val columnLast = "last_name"

        val migration = object : Migration(6, 7) {

            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS old_$user")
                database.execSQL("ALTER TABLE User RENAME TO old_$user")

                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `$user` (
                    `$columnUid` INTEGER NOT NULL,
                    `$columnFirst` TEXT NOT NULL,
                    `$columnLast` TEXT NOT NULL,
                    PRIMARY KEY(`uid`))
                    """)

                database.query("SELECT $columnUid, $columnFirst FROM old_$user").let {
                    val uid = it.getColumnIndexOrThrow(columnUid)
                    val first = it.getColumnIndexOrThrow(columnFirst)
                    val values = ContentValues()
                    while (it.moveToNext()) {
                        values.put(columnUid, it.getInt(uid))
                        values.put(columnFirst, it.getString(first))
                        values.put(columnLast, "none")
                        database.insert(user, SQLiteDatabase.CONFLICT_REPLACE, values)
                    }
                    it.close()
                }
            }
        }
    }
}