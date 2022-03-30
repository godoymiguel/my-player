package com.godamy.myplayer.ui.main

import com.godamy.myplayer.model.MediaItem

data class MainUiState(
    val loading: Boolean = false,
    val mediaItem: List<MediaItem>? = null,
    val navigateTo: MediaItem? = null
)
