package me.aluceps.practiceroom

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import me.aluceps.practiceroom.data.db.AppDatabase
import me.aluceps.practiceroom.data.db.entity.User
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "practice-room.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    private val user by lazy {
        db.userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeView()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

    private fun initializeView() {
        view.text = "Hello World..."
        add.setOnClickListener { insert() }
        show.setOnClickListener { selectAll() }
        clear.setOnClickListener { clear() }
    }

    private fun insert() {
        thread {
            db.using(Runnable {
                val item = User(user.size(), "taro", "ngsw", 24)
                user.insert(item)
                view.text(item)
            })
        }
    }

    private fun selectAll() {
        var text = ""
        thread {
            user.getAll().forEach { text += "$it\n" }
            view.text(text)
        }
    }

    private fun clear() {
        thread {
            db.using(Runnable {
                user.getAll().forEach { user.delete(it) }
            })
            view.text("")
        }
    }

    private fun TextView.text(value: String) {
        post({
            text = value
        })
    }

    private fun TextView.text(user: User) {
        post({
            text = user.toString()
        })
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
