package com.godamy.myplayer.usecases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.domain.Error

class RequestPopularMoviesUseCase(private val repository: MediaRepository) {
    suspend operator fun invoke(): Error? = repository.requestPopularMovies()
}
