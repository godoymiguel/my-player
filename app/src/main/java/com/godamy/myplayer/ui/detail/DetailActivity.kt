package com.godamy.myplayer.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.godamy.myplayer.common.loadUrl
import com.godamy.myplayer.databinding.ActivityDetailBinding
import com.godamy.myplayer.model.MediaItem

class DetailActivity : AppCompatActivity(), DetailPresenter.View {

    private lateinit var binding: ActivityDetailBinding
    private val presenter by lazy { DetailPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mediaItem = requireNotNull(intent.getParcelableExtra<MediaItem>(EXTRA_MOVIE))
        presenter.onCreate(this, mediaItem)
    }

    override fun updateUI(mediaItem: MediaItem) {
        with(binding) {
            mediaItem.let {
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
