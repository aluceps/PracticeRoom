package me.aluceps.practiceroom.data.db

import me.aluceps.practiceroom.data.db.dao.UserDao
import me.aluceps.practiceroom.data.db.entity.User
import javax.inject.Inject

class UserRoomDatabase @Inject constructor(
        private val database: AppDatabase,
        private val userDao: UserDao
) : UserDatabase {

    override fun getAllUsers() = userDao.getAll()

    override fun insertUser(user: User) {
        database.runInTransaction {
            userDao.insert(user)
        }
    }

    override fun deleteUser(user: User) {
        database.runInTransaction {
            userDao.delete(user)
        }
    }

    override fun size(): Int = userDao.size()
}