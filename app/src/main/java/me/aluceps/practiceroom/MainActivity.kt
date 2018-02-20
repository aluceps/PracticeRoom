package me.aluceps.practiceroom

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private lateinit var user: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeDatabase()
        initializeView()
    }

    override fun onDestroy() {
        destroyDatabase()
        super.onDestroy()
    }

    private fun initializeDatabase() {
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "practice-room")
                .fallbackToDestructiveMigration()
                .build()

        user = db.userDao()
    }

    private fun destroyDatabase() {
        db.close()
    }

    private fun initializeView() {
        view.text = "Hello World..."

        add.setOnClickListener {
            insert()
        }
        show.setOnClickListener {
            selectAll()
        }
        clear.setOnClickListener {
            clear()
        }
    }

    private fun insert() {
        var text = ""
        thread {
            val position = user.size() + 1
            val item = User(position, "taro", "ngsw")
            db.using(Runnable {
                user.insert(item)
                text += "${item.uid}: ${item.firstName} ${item.lastName}\n"
            })
            setText(text)
        }
    }

    private fun selectAll() {
        var text = ""
        thread {
            user.getAll().forEach { text += "${it.uid}: ${it.firstName} ${it.lastName}\n" }
            setText(text)
        }
    }

    private fun clear() {
        thread {
            db.using(Runnable {
                user.getAll().forEach { user.delete(it) }
            })
            setText("")
        }
    }

    private fun setText(value: String) {
        view.apply {
            post({
                text = value
            })
        }
    }

    private fun AppDatabase.using(runnable: Runnable) {
        beginTransaction()
        try {
            runnable.run()
            setTransactionSuccessful()
        } finally {
            endTransaction()
        }
    }
}
