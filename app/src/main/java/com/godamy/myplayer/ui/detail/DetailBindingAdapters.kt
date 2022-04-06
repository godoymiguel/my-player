package com.godamy.myplayer.ui.detail

import androidx.databinding.BindingAdapter
import com.godamy.myplayer.data.database.MediaItem

@BindingAdapter("mediaItem")
fun DetailInfoView.setDetailInfo(mediaItem: MediaItem?) {
    mediaItem?.let(this::setDetailInfo)
}
