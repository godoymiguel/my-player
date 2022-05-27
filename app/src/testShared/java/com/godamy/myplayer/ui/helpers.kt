package com.godamy.myplayer.ui

import com.godamy.myplayer.FakeLocationDataSource
import com.godamy.myplayer.FakeMovieDao
import com.godamy.myplayer.FakePermissionChecker
import com.godamy.myplayer.FakeRemoteService
import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.data.RegionRepository
import com.godamy.myplayer.framework.database.MediaItemEntity
import com.godamy.myplayer.framework.database.MediaItemRoomDataSource
import com.godamy.myplayer.framework.server.MediaItemRemote
import com.godamy.myplayer.framework.server.MediaItemServerDataSource

fun buildRepositoryWith(
    localData: List<MediaItemEntity>,
    remoteData: List<MediaItemRemote>
): MediaRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()

    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = MediaItemRoomDataSource(FakeMovieDao(localData))
    val remoteDataSource = MediaItemServerDataSource("fakeApiKey", FakeRemoteService(remoteData))

    return MediaRepository(regionRepository, localDataSource, remoteDataSource)
}

fun buildMediaItemRemote(vararg id: Int) = id.map {
    MediaItemRemote(
        adult = false,
        backdropPath = "",
        genreIds = emptyList(),
        id = it,
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        overview = "Overview $it",
        popularity = 5.0,
        posterPath = "",
        releaseDate = "01/01/2025",
        title = "Title $it",
        video = false,
        voteAverage = 5.1,
        voteCount = 10
    )
}

fun buildMediaItemEntity(vararg id: Int) = id.map {
    MediaItemEntity(
        id = it,
        backdropPath = "",
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        overview = "Overview $it",
        popularity = 5.0,
        posterPath = "",
        releaseDate = "01/01/2025",
        title = "Title $it",
        video = false,
        voteAverage = 5.1,
        favorite = false
    )
}
