package com.godamy.myplayer.data

import com.godamy.myplayer.data.apiservice.RemoteConnection

class MediaRemoteDataSource(private val apiKey: String) {

    suspend fun requestPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
}
