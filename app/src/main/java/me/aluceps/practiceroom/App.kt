package me.aluceps.practiceroom

import dagger.android.support.DaggerApplication
import me.aluceps.practiceroom.di.DaggerAppComponent
import me.aluceps.practiceroom.di.DatabaseModule

open class App : DaggerApplication() {
    override fun applicationInjector() =
            DaggerAppComponent.builder()
                    .application(this)
                    .databaseModule(DatabaseModule.instance)
                    .build()
}