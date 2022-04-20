package com.godamy.myplayer.usecases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.domain.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMovieUseCase @Inject constructor(private val repository: MediaRepository) {
    operator fun invoke(id: Int): Flow<MediaItem> = repository.findById(id)
}
