package com.godamy.myplayer.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.godamy.myplayer.testShared.sampleMovie
import com.godamy.myplayer.testrules.CoroutinesTestRule
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPopularMoviesUserCase: GetPopularMoviesUserCase

    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    private lateinit var viewModel: MainViewModel

    private val movies = listOf(sampleMovie.copy(1))

    @Before
    fun setup() {
        whenever(getPopularMoviesUserCase()).thenReturn(flowOf(movies))
        viewModel = MainViewModel(getPopularMoviesUserCase, requestPopularMoviesUseCase)
    }

    @Test
    fun `State is update with current cachet content immediately`() = runTest {
        viewModel.state.test {
            assertEquals(MainUiState(), awaitItem())
            assertEquals(MainUiState(mediaItem = movies), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is show when screen starts and hidden when it finishes requesting movies`() =
        runTest {
            viewModel.onUiReady()

            viewModel.state.test {
                assertEquals(MainUiState(), awaitItem())
                assertEquals(MainUiState(mediaItem = movies), awaitItem())
                assertEquals(MainUiState(mediaItem = movies, loading = true), awaitItem())
                assertEquals(MainUiState(mediaItem = movies, loading = false), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Popular movies are requested when UI screen starts`() = runTest {
        viewModel.onUiReady()
        runCurrent()

        verify(requestPopularMoviesUseCase).invoke()
    }
}
