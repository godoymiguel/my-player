package com.godamy.myplayer.ui.detail

import androidx.lifecycle.ViewModel
import com.godamy.myplayer.model.MediaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(mediaItem: MediaItem) : ViewModel() {
    private val _state = MutableStateFlow(DetailUiState(mediaItem))
    val state: StateFlow<DetailUiState> = _state.asStateFlow()
}
