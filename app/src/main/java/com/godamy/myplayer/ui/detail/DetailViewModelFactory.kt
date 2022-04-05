package com.godamy.myplayer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godamy.myplayer.model.MediaRepository

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val mediaItemId: Int,
    private val mediaRepository: MediaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(mediaItemId, mediaRepository) as T
}
