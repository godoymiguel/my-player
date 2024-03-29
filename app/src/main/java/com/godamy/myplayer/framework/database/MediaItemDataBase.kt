package com.godamy.myplayer.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MediaItemEntity::class], version = 1, exportSchema = false)
abstract class MediaItemDataBase : RoomDatabase() {
    abstract fun mediaItemDao(): MediaItemDao
}
