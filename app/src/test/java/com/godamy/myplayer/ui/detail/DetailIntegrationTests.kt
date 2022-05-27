package com.godamy.myplayer.ui.detail

import app.cash.turbine.test
import com.godamy.myplayer.ui.buildMediaItemEntity
import com.godamy.myplayer.ui.buildRepositoryWith
import com.godamy.myplayer.framework.database.MediaItemEntity
import com.godamy.myplayer.framework.server.MediaItemRemote
import com.godamy.myplayer.testrules.CoroutinesTestRule
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        val viewModel = buildViewModelWith(
            id = 2,
            localData = buildMediaItemEntity(1, 2)
        )

        viewModel.state.test {
            assertEquals(DetailUiState(), awaitItem())
            assertEquals(2, awaitItem().mediaItem?.id)
            cancel()
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val viewModel = buildViewModelWith(
            2,
            localData = buildMediaItemEntity(1, 2)
        )

        viewModel.onFavoriteClicked()

        viewModel.state.test {
            assertEquals(DetailUiState(), awaitItem())
            assertEquals(false, awaitItem().mediaItem?.favorite)
            assertEquals(true, awaitItem().mediaItem?.favorite)
            cancel()
        }
    }

    private fun buildViewModelWith(
        id: Int,
        localData: List<MediaItemEntity> = emptyList(),
        remoteData: List<MediaItemRemote> = emptyList()
    ): DetailViewModel {

        val repository = buildRepositoryWith(localData, remoteData)

        val findMovieUseCase = FindMovieUseCase(repository)
        val switchFavoriteUseCase = SwitchFavoriteUseCase(repository)
        return DetailViewModel(id, findMovieUseCase, switchFavoriteUseCase)
    }
}