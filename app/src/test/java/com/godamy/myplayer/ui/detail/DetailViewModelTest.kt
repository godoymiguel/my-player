package com.godamy.myplayer.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.godamy.myplayer.testShared.sampleMovie
import com.godamy.myplayer.testrules.CoroutinesTestRule
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
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
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findMovieUseCase: FindMovieUseCase

    @Mock
    lateinit var switchFavoriteUseCase: SwitchFavoriteUseCase

    private lateinit var viewModel: DetailViewModel
    private val movie = sampleMovie.copy(1)

    @Before
    fun setup() {
        whenever(findMovieUseCase(1)).thenReturn(flowOf(movie))
        viewModel = DetailViewModel(1, findMovieUseCase, switchFavoriteUseCase)
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        viewModel.state.test {
            assertEquals(DetailUiState(), awaitItem())
            assertEquals(DetailUiState(mediaItem = movie), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        viewModel.onFavoriteClicked()
        runCurrent()

        verify(switchFavoriteUseCase).invoke(movie)
    }
}
