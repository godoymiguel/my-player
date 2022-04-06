package com.godamy.myplayer.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.godamy.myplayer.common.basicDiffUtil
import com.godamy.myplayer.databinding.ViewMediaItemBinding
import com.godamy.myplayer.data.database.MediaItem

// ListAdapter used to manage recycle view list items
class MediaItemAdapter(
    private val mediaItemClickListener: (MediaItem) -> Unit
) : ListAdapter<MediaItem, MediaItemAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewMediaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaItem = getItem(position)
        holder.bind(mediaItem)
        holder.itemView.setOnClickListener { mediaItemClickListener(mediaItem) }
    }

    class ViewHolder(private val binding: ViewMediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaItem: MediaItem) {
            binding.mediaItem = mediaItem
        }
    }
}
