package com.godamy.myplayer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godamy.myplayer.model.MediaItem
import com.godamy.myplayer.model.MediaItemRepository
import com.godamy.myplayer.ui.common.Filter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val mediaItemRepository: MediaItemRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        refresh()
    }

    private var mediaItems: List<MediaItem> = emptyList()

    private fun refresh() {
        viewModelScope.launch {
            _state.value = MainUiState(loading = true)
            mediaItems = mediaItemRepository.finPopularMovies().results
            _state.value = MainUiState(mediaItem = mediaItems)
        }
    }

    fun onMediaItemClicked(mediaItem: MediaItem) {
        _state.value = MainUiState(navigateTo = mediaItem)
    }

    fun onNavigationDone(){
        _state.value = _state.value.copy(navigateTo = null)
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
