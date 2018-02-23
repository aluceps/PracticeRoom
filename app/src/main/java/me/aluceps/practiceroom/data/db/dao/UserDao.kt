package me.aluceps.practiceroom.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import me.aluceps.practiceroom.data.db.entity.User

@Dao
abstract class UserDao {
    @Query("SELECT count(*) FROM user")
    abstract fun size(): Int

    @Query("SELECT * FROM user")
    abstract fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    abstract fun loadAllByIds(userIds: List<Int>): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    abstract fun findByName(first: String, last: String): User

    @Insert
    abstract fun insert(user: User)

    @Delete
    abstract fun delete(user: User)
}