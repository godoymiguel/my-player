package com.godamy.myplayer.di

import com.godamy.myplayer.ui.detail.DetailViewModelFactory
import com.godamy.myplayer.ui.main.MainViewModelFactory
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object ViewModelModule {

    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUserCase: GetPopularMoviesUserCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUserCase, requestPopularMoviesUseCase)

    @Provides
    fun provideDetailViewModelFactory(
        findMovieUseCase: FindMovieUseCase,
        switchFavoriteUseCase: SwitchFavoriteUseCase
    ): DetailViewModelFactory {
        return DetailViewModelFactory(
            -1,
            findMovieUseCase,
            switchFavoriteUseCase
        )
    }
}
