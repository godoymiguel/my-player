package com.godamy.myplayer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godamy.myplayer.model.MediaRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val mediaRepository: MediaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(mediaRepository) as T
}
