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
import com.godamy.myplayer.databinding.FragmentDetailBinding
import com.godamy.myplayer.ui.common.app
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    @Inject
    lateinit var factory: DetailViewModelAssistedFactory
    private val safeArgs: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels { factory.create(safeArgs.mediaItemId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app.component.inject(this)
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
