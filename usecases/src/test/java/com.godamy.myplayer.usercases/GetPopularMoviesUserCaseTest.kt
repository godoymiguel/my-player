package com.godamy.myplayer.usercases

import com.godamy.myplayer.testShared.sampleMovie
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetPopularMoviesUserCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val movies = flowOf(listOf(sampleMovie.copy(1)))
        val getPopularMoviesUserCase = GetPopularMoviesUserCase(
            mock() { on { popularMovies } doReturn movies }
        )

        val result = getPopularMoviesUserCase()

        assertEquals(movies, result)
    }
}
