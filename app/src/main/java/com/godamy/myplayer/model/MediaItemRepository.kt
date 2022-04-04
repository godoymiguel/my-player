package com.godamy.myplayer.model

import android.app.Application
import com.godamy.myplayer.R
import com.godamy.myplayer.model.apiservice.RemoteConnection

class MediaItemRepository(application: Application) {

    private val apiKey = application.getString(R.string.api_key)

    // TODO IOC
    private val regionRepository = RegionRepository(application)

    suspend fun finPopularMovies() =
        RemoteConnection.service.listPopularMovies(apiKey, regionRepository.findRegion())
}
