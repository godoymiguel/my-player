package com.godamy.myplayer.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.godamy.myplayer.R
import com.godamy.myplayer.databinding.FragmentMainBinding
import com.godamy.myplayer.model.MediaItem
import com.godamy.myplayer.model.MediaItemRepository
import com.godamy.myplayer.ui.main.adapter.MediaItemAdapter
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            MediaItemRepository(
                requireActivity() as AppCompatActivity
            )
        )
    }

    private val mediaItemAdapter = MediaItemAdapter { viewModel.onMediaItemClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            rvMain.adapter = mediaItemAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { binding.updateUI(it) }
        }
    }

    private fun FragmentMainBinding.updateUI(state: MainUiState) {
        progressBar.isVisible = state.loading
        state.mediaItem?.let(mediaItemAdapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(mediaItem: MediaItem) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(mediaItem)
        findNavController().navigate(action)
        // with Activity
        // startActivity<DetailActivity>(DetailActivity.EXTRA_MOVIE to mediaItem)
    }

    //    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val filter = when (item.itemId) {
//            R.id.filter_photos -> Filter.ByType(false)
//            R.id.filter_videos -> Filter.ByType(true)
//            else -> Filter.None
//        }
//
//        viewModel.updateItems(filter)
//        return super.onOptionsItemSelected(item)
//    }
}
