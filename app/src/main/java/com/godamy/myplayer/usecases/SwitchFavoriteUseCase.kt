package com.godamy.myplayer.usecases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.domain.Error
import com.godamy.myplayer.domain.MediaItem

class SwitchFavoriteUseCase(private val repository: MediaRepository) {
    suspend operator fun invoke(mediaItem: MediaItem): Error? = repository.switchFavorite(mediaItem)
}
