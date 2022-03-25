package com.godamy.myplayer.model

import com.google.gson.annotations.SerializedName

data class MediaAllResult(
    val page: Int,
    val results: List<MediaItem>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
