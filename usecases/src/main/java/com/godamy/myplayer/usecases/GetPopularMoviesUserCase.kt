package com.godamy.myplayer.usecases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.domain.MediaItem
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUserCase(private val repository: MediaRepository) {
    operator fun invoke(): Flow<List<MediaItem>> = repository.popularMovies
}
