package com.godamy.myplayer.usecases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.domain.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUserCase @Inject constructor(private val repository: MediaRepository) {
    operator fun invoke(): Flow<List<MediaItem>> = repository.popularMovies
}
