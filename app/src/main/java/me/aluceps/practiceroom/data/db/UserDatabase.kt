package me.aluceps.practiceroom.data.db

import me.aluceps.practiceroom.data.db.entity.User

interface UserDatabase {

    fun all(): List<User>

    fun insert(user: User)

    fun delete(user: User)

    fun count(): Int
}