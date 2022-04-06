package com.godamy.myplayer.data

import com.google.gson.annotations.SerializedName

data class MediaAllResult(
    val page: Int,
    val results: List<MediaItemRemote>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)