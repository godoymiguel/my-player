package com.godamy.myplayer.data

import com.godamy.myplayer.data.datasource.MediaItemLocalDataSource
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import com.godamy.myplayer.domain.Error
import com.godamy.myplayer.domain.MediaItem
import javax.inject.Inject

class MediaRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: MediaItemLocalDataSource,
    private val remoteDataSource: MediaItemRemoteDataSource
) {
    val popularMovies get() = localDataSource.mediaItems

    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val mediaItems = remoteDataSource.requestPopularMovies(region)
            mediaItems.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun switchFavorite(mediaItem: MediaItem): Error? {
        val updateMediaItem = mediaItem.copy(favorite = !mediaItem.favorite)
        return localDataSource.save(listOf(updateMediaItem))
    }
}
