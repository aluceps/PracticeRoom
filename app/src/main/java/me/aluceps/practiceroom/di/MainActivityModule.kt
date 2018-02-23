package me.aluceps.practiceroom.di

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import me.aluceps.practiceroom.MainActivity

@Module
interface MainActivityModule {
    @Binds
    fun providesAppCompatActivity(activity: MainActivity): AppCompatActivity
}