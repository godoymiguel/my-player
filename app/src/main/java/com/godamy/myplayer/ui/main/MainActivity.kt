package com.godamy.myplayer.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.godamy.myplayer.R
import com.godamy.myplayer.common.Logger
import com.godamy.myplayer.common.startActivity
import com.godamy.myplayer.databinding.ActivityMainBinding
import com.godamy.myplayer.model.MediaItem
import com.godamy.myplayer.model.MediaItemRepository
import com.godamy.myplayer.ui.common.Filter
import com.godamy.myplayer.ui.detail.DetailActivity
import com.godamy.myplayer.ui.main.adapter.MediaItemAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Logger {

    private var mediaItems: List<MediaItem> = emptyList()
    private val mediaItemRepository by lazy { MediaItemRepository(this@MainActivity) }

    private val mediaItemAdapter = MediaItemAdapter(this::navigateTo)

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaItemRecycler = binding.rvMain
        progressBar = binding.progressBar

        mediaItemRecycler.adapter = mediaItemAdapter

        lifecycleScope.launch {
            showProgressBar(true)
            mediaItems = mediaItemRepository.finPopularMovies().results
            mediaItemAdapter.submitList(mediaItems)
            showProgressBar(false)
        }

        logD("TEST LOG IN ACTIVITY")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId) {
            R.id.filter_photos -> Filter.ByType(false)
            R.id.filter_videos -> Filter.ByType(true)
            else -> Filter.None
        }

        updateItems(filter)
        return super.onOptionsItemSelected(item)
    }

    private fun navigateTo(mediaItem: MediaItem) {
        startActivity<DetailActivity>(DetailActivity.EXTRA_MOVIE to mediaItem)
    }

    private fun showProgressBar(status: Boolean) {
        progressBar.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun updateItems(filter: Filter = Filter.None) {
        showProgressBar(true)
        mediaItemAdapter.submitList(
            mediaItems.let { media ->
                when (filter) {
                    Filter.None -> media
                    is Filter.ByType -> media.filter { it.video == filter.video }
                }
            }
        )
        showProgressBar(false)
    }
}
