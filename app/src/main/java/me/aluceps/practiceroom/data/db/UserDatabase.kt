package me.aluceps.practiceroom.data.db

import me.aluceps.practiceroom.data.db.entity.User

interface UserDatabase {

    fun getAllUsers(): List<User>

    fun insertUser(user: User)

    fun deleteUser(user: User)

    fun size(): Int
}