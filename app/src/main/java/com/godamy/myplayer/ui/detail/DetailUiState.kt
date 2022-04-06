package com.godamy.myplayer.ui.detail

import com.godamy.myplayer.model.Error
import com.godamy.myplayer.model.database.MediaItem

data class DetailUiState(
    val mediaItem: MediaItem? = null,
    val error: Error? = null
)
