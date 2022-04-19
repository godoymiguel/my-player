package com.godamy.myplayer

import android.app.Application
import com.godamy.myplayer.di.AppComponent
import com.godamy.myplayer.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .factory()
            .create(this)
    }
}
