package com.godamy.myplayer.data

import com.godamy.myplayer.data.apiservice.RemoteConnection
import com.godamy.myplayer.domain.MediaItem

class MediaRemoteDataSource(private val apiKey: String) {

    suspend fun requestPopularMovies(region: String): List<MediaItem> =
        RemoteConnection.service.listPopularMovies(apiKey, region).results.toDomainModel()

    private fun List<MediaItemRemote>.toDomainModel(): List<MediaItem> = map { it.toDomainModel() }

    private fun MediaItemRemote.toDomainModel(): MediaItem = MediaItem(
        id,
        backdropPath?.let { HOST_BACKDROP_PATH + backdropPath } ?: "",
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        HOST_POSTER_PATH + posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        false
    )

    companion object {
        private const val HOST_POSTER_PATH = "https://image.tmdb.org/t/p/w185/"
        private const val HOST_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780/"
    }
}
