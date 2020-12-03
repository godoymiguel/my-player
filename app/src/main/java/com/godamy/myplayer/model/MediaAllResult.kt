package com.godamy.myplayer.model

data class MediaAllResult(
    val page: Int,
    val results: List<MediaItem>,
    val total_pages: Int,
    val total_results: Int
)