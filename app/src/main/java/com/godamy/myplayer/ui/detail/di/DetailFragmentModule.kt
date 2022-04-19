package com.godamy.myplayer.ui.detail.di

import com.godamy.myplayer.ui.detail.DetailViewModelFactory
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
class DetailFragmentModule(private val mediaItemId: Int) {

    @Provides
    fun provideDetailViewModelFactory(
        findMovieUseCase: FindMovieUseCase,
        switchFavoriteUseCase: SwitchFavoriteUseCase
    ): DetailViewModelFactory {
        return DetailViewModelFactory(
            mediaItemId,
            findMovieUseCase,
            switchFavoriteUseCase
        )
    }
}
