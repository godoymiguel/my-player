package com.godamy.myplayer.data.datasource

import arrow.core.Either
import com.godamy.myplayer.domain.Error
import com.godamy.myplayer.domain.MediaItem

interface MediaItemRemoteDataSource {
    suspend fun requestPopularMovies(region: String): Either<Error, List<MediaItem>>
}
