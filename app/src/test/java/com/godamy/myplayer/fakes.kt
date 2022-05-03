package com.godamy.myplayer

import com.godamy.myplayer.data.PermissionChecker
import com.godamy.myplayer.data.PermissionChecker.Permission
import com.godamy.myplayer.data.datasource.LocationDataSource
import com.godamy.myplayer.framework.database.MediaItemDao
import com.godamy.myplayer.framework.database.MediaItemEntity
import com.godamy.myplayer.framework.server.MediaAllResult
import com.godamy.myplayer.framework.server.MediaApiService
import com.godamy.myplayer.framework.server.MediaItemRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeMovieDao(mediaItems: List<MediaItemEntity> = emptyList()) : MediaItemDao {

    private val inMemoryMediaItem = MutableStateFlow(mediaItems)
    private lateinit var findMediaItemFlow: MutableStateFlow<MediaItemEntity>

    override fun getAll(): Flow<List<MediaItemEntity>> = inMemoryMediaItem

    override fun findById(id: Int): Flow<MediaItemEntity> {
        findMediaItemFlow = MutableStateFlow(inMemoryMediaItem.value.first { it.id == id })
        return findMediaItemFlow
    }

    override suspend fun mediaItemCount(): Int = inMemoryMediaItem.value.size

    override suspend fun save(mediaItemEntity: List<MediaItemEntity>) {
        inMemoryMediaItem.value = mediaItemEntity

        if (::findMediaItemFlow.isInitialized) {
            mediaItemEntity.firstOrNull() { it.id == findMediaItemFlow.value.id }
                ?.let { findMediaItemFlow.value = it }
        }

    }
}

class FakeRemoteService(private val mediaItemRemotes: List<MediaItemRemote> = emptyList()) :
    MediaApiService {
    override suspend fun listPopularMovies(apiKey: String, region: String): MediaAllResult =
        MediaAllResult(1, mediaItemRemotes, 1, mediaItemRemotes.size)
}

class FakeLocationDataSource : LocationDataSource {
    private val location = "US"

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    private val permissionGranted = true

    override fun check(permission: Permission): Boolean = permissionGranted
}
