package com.godamy.myplayer.data

import com.godamy.myplayer.data.datasource.MediaItemLocalDataSource
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import com.godamy.myplayer.domain.Error
import com.godamy.myplayer.domain.MediaItem

class MediaRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MediaItemLocalDataSource,
    private val remoteDataSource: MediaItemRemoteDataSource
) {
    val popularMovies = localDataSource.mediaItems

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val mediaItems = remoteDataSource.requestPopularMovies(region)
            localDataSource.save(mediaItems)
        }
    }

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun switchFavorite(mediaItem: MediaItem): Error? = tryCall {
        val updateMediaItem = mediaItem.copy(favorite = !mediaItem.favorite)
        localDataSource.save(updateMediaItem)
    }
}
