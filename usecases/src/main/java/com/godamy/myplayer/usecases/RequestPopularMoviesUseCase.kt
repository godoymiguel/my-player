package com.godamy.myplayer.usecases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.domain.Error
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(private val repository: MediaRepository) {
    suspend operator fun invoke(): Error? = repository.requestPopularMovies()
}
