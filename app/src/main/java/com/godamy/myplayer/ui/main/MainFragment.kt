package com.godamy.myplayer.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.godamy.myplayer.R
import com.godamy.myplayer.common.app
import com.godamy.myplayer.common.launchAndCollect
import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.data.RegionRepository
import com.godamy.myplayer.databinding.FragmentMainBinding
import com.godamy.myplayer.framework.AndroidPermissionChecker
import com.godamy.myplayer.framework.datasource.MediaItemRoomDataSource
import com.godamy.myplayer.framework.datasource.MediaItemServerDataSource
import com.godamy.myplayer.framework.datasource.PlayServiceLocationDataSource
import com.godamy.myplayer.ui.main.adapter.MediaItemAdapter
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        val application = requireActivity().app
        val locationDataSource = PlayServiceLocationDataSource(application)
        val permissionChecker = AndroidPermissionChecker(application)
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)
        val localDataSource = MediaItemRoomDataSource(application.db.mediaItemDao())
        val remoteDataSource = MediaItemServerDataSource(getString(R.string.api_key))
        val repository = MediaRepository(regionRepository, localDataSource, remoteDataSource)
        MainViewModelFactory(
            GetPopularMoviesUserCase(repository),
            RequestPopularMoviesUseCase(repository)
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
        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            binding.loading = state.loading
            binding.mediaItems = state.mediaItem
            binding.error = state.error?.let(mainState::errorToString)
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
