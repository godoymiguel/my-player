package com.godamy.myplayer.ui.detail

import com.godamy.myplayer.model.MediaItem

class DetailPresenter {

    private var view: View? = null

    fun onCreate(view: View, mediaItem: MediaItem) {
        this.view = view
        view.updateUI(mediaItem)
    }

    fun onDestroy() {
        this.view == null
    }

    interface View {
        fun updateUI(mediaItem: MediaItem)
    }
}
