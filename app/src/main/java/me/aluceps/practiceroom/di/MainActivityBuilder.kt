package me.aluceps.practiceroom.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.aluceps.practiceroom.MainActivity

@Module
interface MainActivityBuilder {
    @ContributesAndroidInjector(modules = [
        MainActivityModule::class
    ])
    fun contributeMainActivity(): MainActivity
}