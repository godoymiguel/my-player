package com.godamy.myplayer.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.godamy.myplayer.common.loadUrl
import com.godamy.myplayer.databinding.ViewMediaItemBinding
import com.godamy.myplayer.model.MediaItem
import kotlin.properties.Delegates

//TODO Creando un lamda (MediaItem) -> Unit
class MediaItemAdapter(
    items: List<MediaItem> = emptyList(),
    private val mediaItemClickListener: (MediaItem) -> Unit
) : RecyclerView.Adapter<MediaItemAdapter.ViewHolder>() {

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w185/"
    }
    //TODO Notify by observable when the list of element changes
    var items: List<MediaItem> by Delegates.observable(items){_,_,_ ->
        notifyDataSetChanged()
    }

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
            with(binding) {
                tvMediaTitle.text = mediaItem.title
                ivMediaThumb.loadUrl("$IMAGE_URL${mediaItem.posterPath}")
                ivVideoThumb.visibility = when {
                    mediaItem.video -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }
    }
}