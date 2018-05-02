package me.aluceps.practiceroom

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import me.aluceps.practiceroom.data.db.AppDatabase
import me.aluceps.practiceroom.data.db.UserDatabase
import me.aluceps.practiceroom.data.db.entity.User
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var userDatabase: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        initializeView()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

    private fun initializeView() {
        view.text = "Hello World..."
        view.movementMethod = ScrollingMovementMethod.getInstance()
        add.setOnClickListener { insert() }
        show.setOnClickListener { selectAll() }
        clear.setOnClickListener { clear() }
    }

    private fun insert() {
        thread {
            val item = User(userDatabase.count(), "taro", "nagasawa")
            userDatabase.insert(item)
            view.text(item)
        }
    }

    private fun selectAll() {
        var text = ""
        thread {
            userDatabase.all().forEach { text += "$it\n" }
            view.text(text)
        }
    }

    private fun clear() {
        thread {
            userDatabase.all().forEach { userDatabase.delete(it) }
            view.text("")
        }
    }

    private fun TextView.text(value: String) {
        post {
            text = value
        }
    }

    private fun TextView.text(user: User) {
        post {
            text = user.toString()
        }
    }
}
