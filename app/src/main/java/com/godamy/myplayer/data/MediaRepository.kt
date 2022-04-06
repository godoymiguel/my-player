package com.godamy.myplayer.data

import com.godamy.myplayer.App
import com.godamy.myplayer.R
import com.godamy.myplayer.domain.MediaItem

class MediaRepository(application: App) {

    private val regionRepository = RegionRepository(application)
    private val localDataSource = MediaLocalDataSource(application.db.mediaItemDao())
    private val remoteDataSource = MediaRemoteDataSource(application.getString(R.string.api_key))

    val popularMovies = localDataSource.mediaItems

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val mediaItems = remoteDataSource.requestPopularMovies(regionRepository.findRegion())
            localDataSource.save(mediaItems)
        }
    }

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun switchFavorite(mediaItem: MediaItem): Error? = tryCall {
        val updateMediaItem = mediaItem.copy(favorite = !mediaItem.favorite)
        localDataSource.save(updateMediaItem)
    }
}
