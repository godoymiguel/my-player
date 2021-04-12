package com.godamy.myplayer.ui.detail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import com.godamy.myplayer.R
import com.godamy.myplayer.common.appendInfo
import com.godamy.myplayer.common.loadUrl
import com.godamy.myplayer.databinding.ActivityDetailBinding
import com.godamy.myplayer.model.MediaItem

class DetailActivity : AppCompatActivity() {

    //TODO objeto estatico comparten todas las instancias
    companion object {
        const val EXTRA_MOVIE = "DetailActivity:title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<MediaItem>(EXTRA_MOVIE)
        movie?.let {
            title = it.title
            binding.ivBackdropPath.loadUrl("https://image.tmdb.org/t/p/w780/${it.backdrop_path}")
            binding.tvDetailSummary.text =  it.overview

            bindDetailInfo(binding.tvDetailInfo, it)

        }
    }

//    TODO Spanned String para Agregar mas de una lunea en el mismo TextView
    private fun bindDetailInfo(tvDetailInfo: TextView, movie: MediaItem) {
        tvDetailInfo.text = buildSpannedString {
            appendInfo(applicationContext, R.string.original_language, movie.original_language.toUpperCase())
            appendInfo(applicationContext, R.string.original_title, movie.original_title)
            appendInfo(applicationContext, R.string.release_date, movie.release_date)
            appendInfo(applicationContext, R.string.popularity, movie.popularity.toString())
            appendInfo(applicationContext, R.string.vote_average, movie.vote_average.toString())
        }
    }
}