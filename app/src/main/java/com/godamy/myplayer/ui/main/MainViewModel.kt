package com.godamy.myplayer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godamy.myplayer.model.MediaRepository
import com.godamy.myplayer.model.toError
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            mediaRepository.popularMovies
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { mediaItems -> _state.update { MainUiState(mediaItem = mediaItems) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val error = mediaRepository.finPopularMovies()
            _state.update { it.copy(loading = false, error = error) }
        }
    }

//    fun updateItems(filter: Filter = Filter.None) {
//        _state.value = _state.value.copy(loading = true)
//        _state.value = MainUiState(
//            mediaItem = mediaItems.let { media ->
//                when (filter) {
//                    Filter.None -> media
//                    is Filter.ByType -> media.filter { it.video == filter.video }
//                }
//            }
//        )
//    }
}
