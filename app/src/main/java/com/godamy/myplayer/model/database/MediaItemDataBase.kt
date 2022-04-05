package com.godamy.myplayer.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MediaItem::class], version = 1, exportSchema = false)
abstract class MediaItemDataBase : RoomDatabase() {
    abstract fun mediaItemDao(): MediaItemDao
}