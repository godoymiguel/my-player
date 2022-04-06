package com.godamy.myplayer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val getPopularMoviesUserCase: GetPopularMoviesUserCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(getPopularMoviesUserCase, requestPopularMoviesUseCase) as T
}
