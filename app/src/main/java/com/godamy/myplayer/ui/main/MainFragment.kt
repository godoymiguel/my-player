package com.godamy.myplayer.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.godamy.myplayer.R
import com.godamy.myplayer.databinding.FragmentMainBinding
import com.godamy.myplayer.ui.common.app
import com.godamy.myplayer.ui.common.launchAndCollect
import com.godamy.myplayer.ui.main.adapter.MediaItemAdapter
import com.godamy.myplayer.ui.main.di.MainFragmentComponent
import com.godamy.myplayer.ui.main.di.MainFragmentModule

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var component: MainFragmentComponent
    private val viewModel: MainViewModel by viewModels { component.mainViewModelFactory }

    private lateinit var mainState: MainState
    private val mediaItemAdapter = MediaItemAdapter { mainState.onMediaItemClicked(it.id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.component.plus(MainFragmentModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            rvMain.adapter = mediaItemAdapter
        }

        // DetailFragment e.g. without extension function
        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            binding.loading = state.loading
            binding.mediaItems = state.mediaItem
            binding.error = state.error?.let(mainState::errorToString)
        }

        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }
}
