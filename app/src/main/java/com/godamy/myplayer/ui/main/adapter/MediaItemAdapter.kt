package com.godamy.myplayer.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.godamy.myplayer.common.loadUrl
import com.godamy.myplayer.databinding.ViewMediaItemBinding
import com.godamy.myplayer.model.MediaItem

//TODO Creando un lamda (MediaItem) -> Unit
class MediaItemAdapter(
    var items: List<MediaItem>,
    private val mediaItemClickListener: (MediaItem) -> Unit
) : RecyclerView.Adapter<MediaItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO fun extension
//        val view = parent.infate(R.layout.view_media_item)

//        TODO inflar la vista con binding en adapter
        val binding = ViewMediaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO reciclo las celdas que salen de la pantalla
        val mediaItem = items[position]
        holder.bind(mediaItem)
        //TODO aplicando una lamda
        holder.itemView.setOnClickListener { mediaItemClickListener(mediaItem) }
    }

    //TODO devolver el numero de item
    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ViewMediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaItem: MediaItem) {
            with(binding){
                tvMediaTitle.text = mediaItem.title
                ivMediaThumb.loadUrl("https://image.tmdb.org/t/p/w185/${mediaItem.poster_path}")
                ivVideoThumb.visibility = when {
                    mediaItem.video -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }
    }
}