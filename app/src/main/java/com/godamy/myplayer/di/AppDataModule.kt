package com.godamy.myplayer.di

import com.godamy.myplayer.data.PermissionChecker
import com.godamy.myplayer.data.datasource.LocationDataSource
import com.godamy.myplayer.data.datasource.MediaItemLocalDataSource
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import com.godamy.myplayer.framework.AndroidPermissionChecker
import com.godamy.myplayer.framework.PlayServiceLocationDataSource
import com.godamy.myplayer.framework.database.MediaItemRoomDataSource
import com.godamy.myplayer.framework.server.MediaItemServerDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("UnnecessaryAbstractClass")
abstract class AppDataModule {

    @Binds
    abstract fun bindLocationDataSource(
        locationDataSource: PlayServiceLocationDataSource
    ): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(
        permissionChecker: AndroidPermissionChecker
    ): PermissionChecker

    @Binds
    abstract fun bindMediaItemRemoteDataSource(
        mediaItemRemoteDataSource: MediaItemServerDataSource
    ): MediaItemRemoteDataSource

    @Binds
    abstract fun bindsMediaItemLocalDataSource(
        mediaItemLocalDataSource: MediaItemRoomDataSource
    ): MediaItemLocalDataSource
}
