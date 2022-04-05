package com.godamy.myplayer.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.godamy.myplayer.R
import com.godamy.myplayer.common.app
import com.godamy.myplayer.common.launchAndCollect
import com.godamy.myplayer.databinding.FragmentMainBinding
import com.godamy.myplayer.model.MediaRepository
import com.godamy.myplayer.ui.main.adapter.MediaItemAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            MediaRepository(
                requireActivity().app
            )
        )
    }

    private lateinit var mainState: MainState
    private val mediaItemAdapter = MediaItemAdapter { mainState.onMediaItemClicked(it.id) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            rvMain.adapter = mediaItemAdapter
        }

        // DetailFragment e.g. without extension function
        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.loading = it.loading
            binding.mediaItems = it.mediaItem
        }

        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }
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
