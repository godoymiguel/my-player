package com.godamy.myplayer

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
    }
}
