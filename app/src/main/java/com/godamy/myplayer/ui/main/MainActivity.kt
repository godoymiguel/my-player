package com.godamy.myplayer.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            MediaItemRepository(
                this@MainActivity
            )
        )
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar
    private val mediaItemAdapter = MediaItemAdapter { viewModel.onMediaItemClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaItemRecycler = binding.rvMain
        mediaItemRecycler.adapter = mediaItemAdapter
        progressBar = binding.progressBar

        // Stateflow
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(this@MainActivity::updateUI)
            }
        }

        logD("MAIN ACTIVITY")
    }

    private fun updateUI(state: MainUiState) {
        binding.progressBar.isVisible = state.loading
        state.mediaItem?.let(mediaItemAdapter::submitList)
        state.navigateTo?.let(::navigateTo)
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

        viewModel.updateItems(filter)
        return super.onOptionsItemSelected(item)
    }

    private fun navigateTo(mediaItem: MediaItem) {
        startActivity<DetailActivity>(DetailActivity.EXTRA_MOVIE to mediaItem)
    }
}
