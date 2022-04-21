package com.godamy.myplayer.framework.server

import retrofit2.http.GET
import retrofit2.http.Query

interface MediaApiService {

    @GET("movie/popular")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MediaAllResult
}
