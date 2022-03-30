package com.godamy.myplayer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godamy.myplayer.model.MediaItem

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val mediaItem: MediaItem) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(mediaItem) as T
}
