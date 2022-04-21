package com.godamy.myplayer.usercases

import com.godamy.myplayer.testShared.sampleMovie
import com.godamy.myplayer.usecases.FindMovieUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindMovieUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val movie = flowOf(sampleMovie.copy(1))
        val findMovieUseCase = FindMovieUseCase(
            mock() { on { findById(1) } doReturn movie }
        )

        val result = findMovieUseCase(1)

        assertEquals(movie, result)
    }
}
