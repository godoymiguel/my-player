package com.godamy.myplayer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godamy.myplayer.model.MediaItemRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val mediaItemRepository: MediaItemRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(mediaItemRepository) as T
}
