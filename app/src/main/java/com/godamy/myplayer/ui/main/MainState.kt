package com.godamy.myplayer.ui.main

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.godamy.myplayer.model.MediaItem
import com.godamy.myplayer.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainState(
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

    fun onMediaItemClicked(mediaItem: MediaItem) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(mediaItem)
        navController.navigate(action)
        // with Activity
        // startActivity<DetailActivity>(DetailActivity.EXTRA_MOVIE to mediaItem)
    }
}

fun Fragment.buildMainState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(scope, navController, locationPermissionRequester)
