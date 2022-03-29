package com.godamy.myplayer.model

import androidx.appcompat.app.AppCompatActivity
import com.godamy.myplayer.R
import com.godamy.myplayer.model.apiservice.RemoteConnection

class MediaItemRepository(activity: AppCompatActivity) {

    private val apiKey = activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)

    suspend fun finPopularMovies() =
        RemoteConnection.service.listPopularMovies(apiKey, regionRepository.findRegion())
}
