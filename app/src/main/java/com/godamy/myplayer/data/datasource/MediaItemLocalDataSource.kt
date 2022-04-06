package com.godamy.myplayer.data.datasource

import com.godamy.myplayer.domain.MediaItem
import kotlinx.coroutines.flow.Flow

interface MediaItemLocalDataSource {
    val mediaItems: Flow<List<MediaItem>>
    suspend fun isEmpty(): Boolean
    suspend fun save(mediaItems: List<MediaItem>)
    suspend fun save(mediaItem: MediaItem)
    fun findById(id: Int): Flow<MediaItem>
}
