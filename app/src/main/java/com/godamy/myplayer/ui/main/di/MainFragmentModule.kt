package com.godamy.myplayer.ui.main.di

import com.godamy.myplayer.ui.main.MainViewModelFactory
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase
import dagger.Module
import dagger.Provides

@Module
object MainFragmentModule {

    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUserCase: GetPopularMoviesUserCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUserCase, requestPopularMoviesUseCase)
}
