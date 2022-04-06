package com.godamy.myplayer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godamy.myplayer.model.MediaRepository
import com.godamy.myplayer.model.toError
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(mediaItemId: Int, private val mediaRepository: MediaRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            mediaRepository.findById(mediaItemId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { mediaItem -> _state.update { DetailUiState(mediaItem) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.mediaItem?.let { mediaItem ->
                val error = mediaRepository.switchFavorite(mediaItem)
                _state.update { it.copy(error = error) }
            }
        }
    }
}
