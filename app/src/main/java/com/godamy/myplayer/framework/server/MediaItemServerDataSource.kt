package com.godamy.myplayer.framework.server

import arrow.core.Either
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import com.godamy.myplayer.di.ApiKey
import com.godamy.myplayer.domain.Error
import com.godamy.myplayer.domain.MediaItem
import com.godamy.myplayer.framework.common.Logger
import com.godamy.myplayer.framework.common.tryCall
import javax.inject.Inject

class MediaItemServerDataSource @Inject constructor(@ApiKey private val apiKey: String) :
    MediaItemRemoteDataSource, Logger {

    override suspend fun requestPopularMovies(region: String): Either<Error, List<MediaItem>> =
        tryCall {
            logI("Region: $region")
            RemoteConnection.service.listPopularMovies(
                apiKey,
                region
            ).results.toDomainModel()
        }

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
