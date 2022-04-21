package com.godamy.myplayer.usercases

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.testShared.sampleMovie
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchFavoriteUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val movie = sampleMovie.copy(1)
        val mediaRepository = mock<MediaRepository>()
        val switchFavoriteUseCase = SwitchFavoriteUseCase(mediaRepository)

        switchFavoriteUseCase(movie)

        verify(mediaRepository).switchFavorite(movie)
    }
}
