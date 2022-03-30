package com.godamy.myplayer.ui.main

import com.godamy.myplayer.common.Logger
import com.godamy.myplayer.model.MediaItem
import com.godamy.myplayer.model.MediaItemRepository
import com.godamy.myplayer.ui.common.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val scope: CoroutineScope,
    private val mediaItemRepository: MediaItemRepository
) : Logger {

    private var view: View? = null
    private var mediaItems: List<MediaItem> = emptyList()

    fun onCreate(view: View) {
        this.view = view

        scope.launch {
            view.showProgressBar(true)
            mediaItems = mediaItemRepository.finPopularMovies().results
            view.updateData(mediaItems)
            view.showProgressBar(false)
        }
        logD("onCreate")
    }

    fun onDestroy() {
        logD("onDestroy")
        this.view = null
    }

    fun onMediaItemClicked(mediaItem: MediaItem) {
        logD("onMediaItemClicked")
        view?.navigateTo(mediaItem)
    }

    fun updateItems(filter: Filter = Filter.None) {
        view?.showProgressBar(true)
        view?.updateData(
            mediaItems.let { media ->
                when (filter) {
                    Filter.None -> media
                    is Filter.ByType -> media.filter { it.video == filter.video }
                }
            }
        )
        view?.showProgressBar(false)
    }

    interface View {
        fun showProgressBar(status: Boolean)
        fun navigateTo(mediaItem: MediaItem)
        fun updateData(mediaItems: List<MediaItem>)
    }
}
