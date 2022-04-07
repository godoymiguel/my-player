package com.godamy.myplayer.usecases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.domain.MediaItem
import kotlinx.coroutines.flow.Flow

class FindMovieUseCase(private val repository: MediaRepository) {
    operator fun invoke(id: Int): Flow<MediaItem> = repository.findById(id)
}
