package com.godamy.myplayer.model

import android.Manifest
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity

class RegionRepository(activity: AppCompatActivity) {

    // TODO IOC
    private val locationDataSource: LocationDataSource = PlayServiceLocationDataSource(activity)
    private val geocoder = Geocoder(activity)

    private val coarsePermisionChecker =
        PermissionChecker(activity, Manifest.permission.ACCESS_COARSE_LOCATION)

    suspend fun findRegion(): String = findLastLocation().toRegion()

    // Set default location when is false
    private suspend fun findLastLocation(): Location? {
        val success = coarsePermisionChecker.request()
        return if (success) locationDataSource.findLastLocation() else null
    }

    private fun Location?.toRegion(): String {
        val address = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return address?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

    companion object {
        private const val DEFAULT_REGION = "US"
    }
}
