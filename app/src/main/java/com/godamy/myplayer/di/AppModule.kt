package com.godamy.myplayer.di

import android.app.Application
import androidx.room.Room
import com.godamy.myplayer.R
import com.godamy.myplayer.data.PermissionChecker
import com.godamy.myplayer.data.datasource.LocationDataSource
import com.godamy.myplayer.data.datasource.MediaItemLocalDataSource
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import com.godamy.myplayer.framework.AndroidPermissionChecker
import com.godamy.myplayer.framework.PlayServiceLocationDataSource
import com.godamy.myplayer.framework.database.MediaItemDataBase
import com.godamy.myplayer.framework.database.MediaItemRoomDataSource
import com.godamy.myplayer.framework.server.MediaItemServerDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MediaItemDataBase::class.java,
        "media-item-db"
    ).build()

    @Provides
    fun provideLocationDataSource(app: Application): LocationDataSource =
        PlayServiceLocationDataSource(app)

    @Provides
    fun providePermissionChecker(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

    @Provides
    fun provideMediaItemRemoteDataSource(@ApiKey apiKey: String): MediaItemRemoteDataSource =
        MediaItemServerDataSource(apiKey)

    @Provides
    fun provideMediaItemLocalDataSource(db: MediaItemDataBase): MediaItemLocalDataSource =
        MediaItemRoomDataSource(db.mediaItemDao())
}
