package com.godamy.myplayer.model

import com.godamy.myplayer.model.database.MediaItem
import com.godamy.myplayer.model.database.MediaItemDao

class MediaLocalDataSource(private val dao: MediaItemDao) {

    val mediaItems = dao.getAll()

    suspend fun isEmpty(): Boolean = dao.mediaItemCount() == 0

    suspend fun save(mediaItem: List<MediaItem>) {
        dao.create(mediaItem)
    }

    fun findById(id: Int) = dao.findById(id)
}
