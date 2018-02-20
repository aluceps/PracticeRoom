package me.aluceps.practiceroom

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeDatabase()
        initializeView()
    }

    private fun initializeDatabase() {
        dao = App().database.userDao()
    }

    private fun initializeView() {
        view.text = "Hello World..."

        add.setOnClickListener { }
        show.setOnClickListener { }
        clear.setOnClickListener { }
    }
}
