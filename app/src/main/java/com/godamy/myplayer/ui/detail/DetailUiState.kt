package com.godamy.myplayer.ui.detail

import com.godamy.myplayer.data.Error
import com.godamy.myplayer.domain.MediaItem

data class DetailUiState(
    val mediaItem: MediaItem? = null,
    val error: Error? = null
)
