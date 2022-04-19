package com.godamy.myplayer.di

import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object UserCaseModule {

    @Provides
    fun provideGetPopularMoviesUserCase(mediaRepository: MediaRepository) =
        GetPopularMoviesUserCase(mediaRepository)

    @Provides
    fun provideFindMovieUseCase(mediaRepository: MediaRepository) =
        FindMovieUseCase(mediaRepository)

    @Provides
    fun provideRequestPopularMoviesUseCase(mediaRepository: MediaRepository) =
        RequestPopularMoviesUseCase(mediaRepository)

    @Provides
    fun provideSwitchFavoriteUseCase(mediaRepository: MediaRepository) =
        SwitchFavoriteUseCase(mediaRepository)
}
