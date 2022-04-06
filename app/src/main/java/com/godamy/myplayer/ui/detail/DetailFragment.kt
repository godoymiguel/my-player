package com.godamy.myplayer.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.godamy.myplayer.R
import com.godamy.myplayer.common.app
import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.data.RegionRepository
import com.godamy.myplayer.databinding.FragmentDetailBinding
import com.godamy.myplayer.framework.AndroidPermissionChecker
import com.godamy.myplayer.framework.PlayServiceLocationDataSource
import com.godamy.myplayer.framework.database.MediaItemRoomDataSource
import com.godamy.myplayer.framework.server.MediaItemServerDataSource
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        val application = requireActivity().app
        val locationDataSource = PlayServiceLocationDataSource(application)
        val permissionChecker = AndroidPermissionChecker(application)
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)
        val localDataSource = MediaItemRoomDataSource(application.db.mediaItemDao())
        val remoteDataSource = MediaItemServerDataSource(getString(R.string.api_key))
        val repository = MediaRepository(regionRepository, localDataSource, remoteDataSource)
        DetailViewModelFactory(
            safeArgs.mediaItemId,
            FindMovieUseCase(repository),
            SwitchFavoriteUseCase(repository)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        // MainFragment e.g with without extension function
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.mediaItem?.let { binding.mediaItem = it }
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.btnFab.setOnClickListener { viewModel.onFavoriteClicked() }
    }
}
