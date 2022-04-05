package com.godamy.myplayer.ui.main

import com.godamy.myplayer.model.database.MediaItem

data class MainUiState(
    val loading: Boolean = false,
    val mediaItem: List<MediaItem>? = null
)
