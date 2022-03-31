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
import com.godamy.myplayer.common.loadUrl
import com.godamy.myplayer.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(requireNotNull(safeArgs.mediaItem))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { binding.updateUI(it) }
            }
        }
    }

    private fun FragmentDetailBinding.updateUI(state: DetailUiState) {
        state.mediaItem.let {
            toolbar.title = it.title
            val background = it.backdropPath ?: it.posterPath
            ivBackdropPath.loadUrl("$IMAGE_URL$background")
            tvDetailSummary.text = it.overview
            tvDetailInfo.setDetailInfo(it)
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w780/"
    }
}
