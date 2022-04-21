package com.godamy.myplayer.data.datasource

import com.godamy.myplayer.domain.Error
import com.godamy.myplayer.domain.MediaItem
import kotlinx.coroutines.flow.Flow

interface MediaItemLocalDataSource {
    val mediaItems: Flow<List<MediaItem>>
    suspend fun isEmpty(): Boolean
    suspend fun save(mediaItems: List<MediaItem>): Error?
    fun findById(id: Int): Flow<MediaItem>
}
