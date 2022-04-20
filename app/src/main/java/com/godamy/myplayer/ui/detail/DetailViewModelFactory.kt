package com.godamy.myplayer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory @AssistedInject constructor(
    @Assisted private val mediaItemId: Int,
    private val findMovieUseCase: FindMovieUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(mediaItemId, findMovieUseCase, switchFavoriteUseCase) as T
}
