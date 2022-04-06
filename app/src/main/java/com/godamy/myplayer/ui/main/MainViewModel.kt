package com.godamy.myplayer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godamy.myplayer.data.toError
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    getPopularMoviesUserCase: GetPopularMoviesUserCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUserCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { mediaItems -> _state.update { MainUiState(mediaItem = mediaItems) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val error = requestPopularMoviesUseCase()
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
