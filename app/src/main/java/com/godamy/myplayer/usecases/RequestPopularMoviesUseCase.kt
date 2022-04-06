package com.godamy.myplayer.usecases

import com.godamy.myplayer.data.Error
import com.godamy.myplayer.data.MediaRepository

class RequestPopularMoviesUseCase(private val repository: MediaRepository) {
    suspend operator fun invoke(): Error? = repository.requestPopularMovies()
}
