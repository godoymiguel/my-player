package com.godamy.myplayer.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.godamy.myplayer.common.loadUrl
import com.godamy.myplayer.databinding.ActivityDetailBinding
import com.godamy.myplayer.model.MediaItem

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityDetailBinding.inflate(layoutInflater).run {
            setContentView(root)

            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val movie = intent.getParcelableExtra<MediaItem>(EXTRA_MOVIE)
            movie?.let {
                title = it.title
                val background = it.backdropPath ?: it.posterPath
                ivBackdropPath.loadUrl("$IMAGE_URL$background")
                tvDetailSummary.text = it.overview
                tvDetailInfo.setDetailInfo(it)
            }
        }
    }

    companion object {
        const val EXTRA_MOVIE = "DetailActivity:title"
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w780/"
    }
}
