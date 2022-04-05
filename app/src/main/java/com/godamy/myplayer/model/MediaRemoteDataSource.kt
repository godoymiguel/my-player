package com.godamy.myplayer.model

import com.godamy.myplayer.model.apiservice.RemoteConnection

class MediaRemoteDataSource(private val apiKey: String) {

    suspend fun finPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
}
