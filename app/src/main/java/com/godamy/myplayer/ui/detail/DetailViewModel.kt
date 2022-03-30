package com.godamy.myplayer.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.godamy.myplayer.model.MediaItem

class DetailViewModel(mediaItem: MediaItem) : ViewModel() {
    private val _state = MutableLiveData(DetailUiState(mediaItem))
    val state: LiveData<DetailUiState> get() = _state
}
