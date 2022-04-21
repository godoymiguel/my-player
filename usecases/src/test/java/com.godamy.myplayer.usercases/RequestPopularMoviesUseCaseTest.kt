package com.godamy.myplayer.usercases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestPopularMoviesUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val mediaRepository = mock<MediaRepository>()
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(mediaRepository)

        requestPopularMoviesUseCase()

        verify(mediaRepository).requestPopularMovies()
    }
}
