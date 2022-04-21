package com.godamy.myplayer.ui.main

import com.godamy.myplayer.domain.Error
import com.godamy.myplayer.domain.MediaItem

data class MainUiState(
    val loading: Boolean = false,
    val mediaItem: List<MediaItem>? = null,
    val error: Error? = null
)
