package com.godamy.myplayer.di

import android.app.Application
import androidx.room.Room
import com.godamy.myplayer.R
import com.godamy.myplayer.framework.database.MediaItemDataBase
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
    @Singleton
    fun provideMediaItemDao(dataBase: MediaItemDataBase) = dataBase.mediaItemDao()
}
