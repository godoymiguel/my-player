package com.godamy.myplayer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godamy.myplayer.model.MediaRepository
import com.godamy.myplayer.model.database.MediaItem
import com.godamy.myplayer.ui.common.Filter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private var mediaItems: List<MediaItem> = emptyList()

    init {
        viewModelScope.launch {
            mediaRepository.popularMovies.collect {
                mediaItems = it
                _state.value = MainUiState(mediaItem = mediaItems)
            }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = MainUiState(loading = true)
            mediaRepository.finPopularMovies()
        }
    }

    fun updateItems(filter: Filter = Filter.None) {
        _state.value = _state.value.copy(loading = true)
        _state.value = MainUiState(
            mediaItem = mediaItems.let { media ->
                when (filter) {
                    Filter.None -> media
                    is Filter.ByType -> media.filter { it.video == filter.video }
                }
            }
        )
    }
}
