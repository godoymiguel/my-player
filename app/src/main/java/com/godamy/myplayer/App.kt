package com.godamy.myplayer

import android.app.Application
import androidx.room.Room
import com.godamy.myplayer.data.database.MediaItemDataBase

class App : Application() {
    lateinit var db: MediaItemDataBase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MediaItemDataBase::class.java,
            "media-item-db"
        ).build()
    }
}
