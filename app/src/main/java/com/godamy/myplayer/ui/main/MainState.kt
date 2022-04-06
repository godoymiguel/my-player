package com.godamy.myplayer.ui.main

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.godamy.myplayer.R
import com.godamy.myplayer.domain.Error
import com.godamy.myplayer.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: PermissionRequester
) {

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun onMediaItemClicked(mediaItemId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(mediaItemId)
        navController.navigate(action)
        // with Activity
        // startActivity<DetailActivity>(DetailActivity.EXTRA_MOVIE to mediaItem)
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}

fun Fragment.buildMainState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(context, scope, navController, locationPermissionRequester)
