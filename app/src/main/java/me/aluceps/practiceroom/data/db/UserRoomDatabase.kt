package me.aluceps.practiceroom.data.db

import me.aluceps.practiceroom.data.db.dao.UserDao
import me.aluceps.practiceroom.data.db.entity.User
import javax.inject.Inject

class UserRoomDatabase @Inject constructor(
        private val database: AppDatabase,
        private val userDao: UserDao
) : UserDatabase {

    override fun all() = userDao.all()

    override fun insert(user: User) {
        database.runInTransaction {
            userDao.insert(user)
        }
    }

    override fun delete(user: User) {
        database.runInTransaction {
            userDao.delete(user)
        }
    }

    override fun count(): Int = userDao.count()
}