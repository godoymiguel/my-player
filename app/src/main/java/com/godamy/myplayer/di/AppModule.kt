package com.godamy.myplayer.di

import android.app.Application
import androidx.room.Room
import com.godamy.myplayer.R
import com.godamy.myplayer.framework.database.MediaItemDataBase
import com.godamy.myplayer.framework.server.MediaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

    @Provides
    @Singleton
    fun provideRemoteService(): MediaApiService {
        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create()
    }
}
