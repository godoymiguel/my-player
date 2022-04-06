package com.godamy.myplayer.data.datasource

import com.godamy.myplayer.domain.MediaItem

interface MediaItemRemoteDataSource {
    suspend fun requestPopularMovies(region: String): List<MediaItem>
}
