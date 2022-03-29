package com.godamy.myplayer.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.godamy.myplayer.model.MediaItem

object DiffUtilMediaItemCallback : DiffUtil.ItemCallback<MediaItem>() {
    override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean =
        oldItem == newItem
}
