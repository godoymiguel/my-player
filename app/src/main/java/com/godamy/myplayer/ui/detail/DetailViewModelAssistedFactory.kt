package com.godamy.myplayer.ui.detail

import dagger.assisted.AssistedFactory

@AssistedFactory
interface DetailViewModelAssistedFactory {
    fun create(mediaItemId: Int): DetailViewModelFactory
}
