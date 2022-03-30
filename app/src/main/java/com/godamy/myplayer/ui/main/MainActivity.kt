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

class MainActivity : AppCompatActivity(), Logger, MainPresenter.View {

    private val presenter by lazy {
        MainPresenter(
            lifecycleScope,
            MediaItemRepository(this@MainActivity)
        )
    }
    private val mediaItemAdapter = MediaItemAdapter { presenter.onMediaItemClicked(it) }

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaItemRecycler = binding.rvMain
        mediaItemRecycler.adapter = mediaItemAdapter
        progressBar = binding.progressBar

        presenter.onCreate(this)
        logD("MAIN ACTIVITY")
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

        presenter.updateItems(filter)
        return super.onOptionsItemSelected(item)
    }

    override fun navigateTo(mediaItem: MediaItem) {
        startActivity<DetailActivity>(DetailActivity.EXTRA_MOVIE to mediaItem)
    }

    override fun showProgressBar(status: Boolean) {
        progressBar.visibility = if (status) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateData(mediaItems: List<MediaItem>) {
        mediaItemAdapter.submitList(mediaItems)
    }
}
