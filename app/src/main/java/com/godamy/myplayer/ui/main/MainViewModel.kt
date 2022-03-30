package com.godamy.myplayer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godamy.myplayer.model.MediaItem
import com.godamy.myplayer.model.MediaItemRepository
import com.godamy.myplayer.ui.common.Filter
import kotlinx.coroutines.launch

class MainViewModel(
    private val mediaItemRepository: MediaItemRepository
) : ViewModel() {

    private val _state = MutableLiveData(MainUiState())
    val state: LiveData<MainUiState>
        get() {
            if (_state.value?.mediaItem == null) {
                refresh()
            }
            return _state
        }

    private var mediaItems: List<MediaItem> = emptyList()

    private fun refresh() {
        viewModelScope.launch {
            _state.value = MainUiState(loading = true, navigateTo = null)
            mediaItems = mediaItemRepository.finPopularMovies().results
            _state.value = MainUiState(mediaItem = mediaItems)
        }
    }

    fun onMediaItemClicked(mediaItem: MediaItem) {
        _state.value = MainUiState(navigateTo = mediaItem)
    }

    fun updateItems(filter: Filter = Filter.None) {
        _state.value = _state.value?.copy(loading = true, navigateTo = null)
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
