package com.godamy.myplayer.di

import android.app.Application
import androidx.room.Room
import com.godamy.myplayer.R
import com.godamy.myplayer.framework.database.MediaItemDao
import com.godamy.myplayer.framework.database.MediaItemDataBase
import com.godamy.myplayer.framework.server.MediaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    @ApiUrl
    fun provideApiUrl(): String = "http://localhost:8080"

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String): MediaApiService {
        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create()
    }
}