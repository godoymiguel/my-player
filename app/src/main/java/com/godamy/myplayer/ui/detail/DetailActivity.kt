package com.godamy.myplayer.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.godamy.myplayer.common.loadUrl
import com.godamy.myplayer.databinding.ActivityDetailBinding
import com.godamy.myplayer.model.MediaItem

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(requireNotNull(intent.getParcelableExtra(EXTRA_MOVIE)))
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.state.observe(this) {
            updateUI(it.mediaItem)
        }
    }

    private fun updateUI(mediaItem: MediaItem) {
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
