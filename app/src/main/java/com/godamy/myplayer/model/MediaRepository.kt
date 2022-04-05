package com.godamy.myplayer.model

import com.godamy.myplayer.App
import com.godamy.myplayer.R
import com.godamy.myplayer.model.database.MediaItem

class MediaRepository(application: App) {

    private val regionRepository = RegionRepository(application)
    private val localDataSource = MediaLocalDataSource(application.db.mediaItemDao())
    private val remoteDataSource = MediaRemoteDataSource(application.getString(R.string.api_key))

    val popularMovies = localDataSource.mediaItems

    suspend fun finPopularMovies() {
        if (localDataSource.isEmpty()) {
            val mediaItems = remoteDataSource.finPopularMovies(regionRepository.findRegion())
            localDataSource.save(mediaItems.results.map { it.toLocalModel() })
        }
    }

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun switchFavorite(mediaItem: MediaItem) {
        val updateMediaItem = mediaItem.copy(favorite = !mediaItem.favorite)
        localDataSource.save(updateMediaItem)
    }

    private fun MediaItemRemote.toLocalModel(): MediaItem = MediaItem(
        id,
        backdropPath,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        false
    )
}
