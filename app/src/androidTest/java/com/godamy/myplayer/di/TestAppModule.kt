package com.godamy.myplayer.di

import android.app.Application
import androidx.room.Room
import com.godamy.myplayer.FakeMovieDao
import com.godamy.myplayer.FakeRemoteService
import com.godamy.myplayer.R
import com.godamy.myplayer.framework.database.MediaItemDao
import com.godamy.myplayer.framework.database.MediaItemDataBase
import com.godamy.myplayer.framework.server.MediaApiService
import com.godamy.myplayer.ui.buildMediaItemRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
object TestAppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        MediaItemDataBase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideMediaItemDao(db: MediaItemDataBase): MediaItemDao = db.mediaItemDao()

    @Provides
    @Singleton
    fun provideRemoteService(): MediaApiService = FakeRemoteService(buildMediaItemRemote(1,2,3,4,5,6))
}