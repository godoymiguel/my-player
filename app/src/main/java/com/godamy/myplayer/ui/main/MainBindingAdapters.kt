package com.godamy.myplayer.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.godamy.myplayer.model.database.MediaItem
import com.godamy.myplayer.ui.main.adapter.MediaItemAdapter

@BindingAdapter("items")
fun RecyclerView.setItems(mediaItems: List<MediaItem>?) {
    mediaItems?.let {
        (adapter as? MediaItemAdapter)?.submitList(it)
    }
}
