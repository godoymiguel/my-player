package com.godamy.myplayer.ui.main

import app.cash.turbine.test
import com.godamy.myplayer.buildMediaItemEntity
import com.godamy.myplayer.buildMediaItemRemote
import com.godamy.myplayer.buildRepositoryWith
import com.godamy.myplayer.framework.database.MediaItemEntity
import com.godamy.myplayer.framework.server.MediaItemRemote
import com.godamy.myplayer.testrules.CoroutinesTestRule
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = buildMediaItemRemote(1, 2, 3, 4, 5)
        val viewModel: MainViewModel = buildViewModelWith(remoteData = remoteData)

        viewModel.onUiReady()

        viewModel.state.test {
            assertEquals(MainUiState(), awaitItem())
            assertEquals(MainUiState(mediaItem = emptyList()), awaitItem())
            assertEquals(MainUiState(mediaItem = emptyList(), loading = true), awaitItem())
            assertEquals(MainUiState(mediaItem = emptyList(), loading = false), awaitItem())
            awaitItem().mediaItem?.let {
                assertEquals(1, it[0].id)
                assertEquals(2, it[1].id)
                assertEquals(3, it[2].id)
                assertEquals(4, it[3].id)
                assertEquals(5, it[4].id)
            }
            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val remoteData = buildMediaItemRemote(1, 2, 3, 4, 5)
        val localData = buildMediaItemEntity(1, 2, 3, 4, 5)
        val viewModel = buildViewModelWith(localData, remoteData)

        viewModel.state.test {
            assertEquals(MainUiState(), awaitItem())
            awaitItem().mediaItem?.let {
                assertEquals(1, it[0].id)
                assertEquals(2, it[1].id)
                assertEquals(3, it[2].id)
                assertEquals(4, it[3].id)
                assertEquals(5, it[4].id)
            }
            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<MediaItemEntity> = emptyList(),
        remoteData: List<MediaItemRemote> = emptyList()
    ): MainViewModel {

        val repository = buildRepositoryWith(localData, remoteData)

        val getPopularMoviesUserCase = GetPopularMoviesUserCase(repository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(repository)

        return MainViewModel(getPopularMoviesUserCase, requestPopularMoviesUseCase)
    }
}