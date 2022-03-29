package com.godamy.myplayer.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.godamy.myplayer.common.basicDiffUtil
import com.godamy.myplayer.common.loadUrl
import com.godamy.myplayer.databinding.ViewMediaItemBinding
import com.godamy.myplayer.model.MediaItem

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
            with(binding) {
                tvMediaTitle.text = mediaItem.title
                ivMediaThumb.loadUrl("$IMAGE_URL${mediaItem.posterPath}")
                ivVideoThumb.visibility =
                    if (mediaItem.video) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w185/"
    }
}
