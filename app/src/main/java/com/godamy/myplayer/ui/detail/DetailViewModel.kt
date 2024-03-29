package com.godamy.myplayer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godamy.myplayer.di.MediaItemId
import com.godamy.myplayer.framework.common.toError
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @MediaItemId private val mediaItemId: Int,
    findMovieUseCase: FindMovieUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(mediaItemId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { mediaItem -> _state.update { DetailUiState(mediaItem) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.mediaItem?.let { mediaItem ->
                val error = switchFavoriteUseCase(mediaItem)
                _state.update { it.copy(error = error) }
            }
        }
    }
}
