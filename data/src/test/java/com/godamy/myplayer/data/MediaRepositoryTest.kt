package com.godamy.myplayer.data

import arrow.core.right
import com.godamy.myplayer.data.datasource.MediaItemLocalDataSource
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import com.godamy.myplayer.testShared.sampleMovie
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MediaRepositoryTest {

    @Mock
    lateinit var regionRepository: RegionRepository

    @Mock
    lateinit var localDataSource: MediaItemLocalDataSource

    @Mock
    lateinit var remoteDataSource: MediaItemRemoteDataSource

    private lateinit var mediaRepository: MediaRepository

    @Before
    fun setUp() {
        mediaRepository = MediaRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Popular movies are taken from local data source if available`(): Unit = runBlocking {
        val localMovie = flowOf(listOf(sampleMovie.copy(1)))
        whenever(localDataSource.mediaItems).thenReturn(localMovie)

        val result = mediaRepository.popularMovies

        assertEquals(localMovie, result)
    }

    @Test
    fun `Popular movies are saved to local data source when it's empty`(): Unit = runBlocking {
        val remoteMovie = listOf(sampleMovie.copy(2))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.findLastRegion()).thenReturn(RegionRepository.DEFAULT_REGION)
        whenever(remoteDataSource.requestPopularMovies(any())).thenReturn(remoteMovie.right())

        mediaRepository.requestPopularMovies()

        verify(localDataSource).save(remoteMovie)
    }

    @Test
    fun `Finding a movie by id is done in local data source`(): Unit = runBlocking {
        val movie = flowOf(sampleMovie.copy(3))
        whenever(localDataSource.findById(3)).thenReturn(movie)

        val result = mediaRepository.findById(3)

        assertEquals(movie, result)
    }

    @Test
    fun `Switching favorite updates local data source`(): Unit = runBlocking {
        val movie = sampleMovie.copy(4)

        mediaRepository.switchFavorite(movie)

        verify(localDataSource).save(argThat { get(0).id == 4 })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite movie`(): Unit = runBlocking {
        val movie = sampleMovie.copy(favorite = false)

        mediaRepository.switchFavorite(movie)

        verify(localDataSource).save(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite movie`(): Unit = runBlocking {
        val movie = sampleMovie.copy(favorite = true)

        mediaRepository.switchFavorite(movie)

        verify(localDataSource).save(argThat { !get(0).favorite })
    }
}
