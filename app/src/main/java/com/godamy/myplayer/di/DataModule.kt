package com.godamy.myplayer.di

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.data.PermissionChecker
import com.godamy.myplayer.data.RegionRepository
import com.godamy.myplayer.data.datasource.LocationDataSource
import com.godamy.myplayer.data.datasource.MediaItemLocalDataSource
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
object DataModule {

    @Provides
    fun provideRegionRepository(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun provideMediaRepository(
        regionRepository: RegionRepository,
        localDataSource: MediaItemLocalDataSource,
        remoteDataSource: MediaItemRemoteDataSource
    ) = MediaRepository(regionRepository, localDataSource, remoteDataSource)
}
