package com.godamy.myplayer.data

import android.Manifest
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.godamy.myplayer.data.datasource.LocationDataSource

class RegionRepository(application: Application) {

    // TODO IOC
    private val locationDataSource: LocationDataSource = PlayServiceLocationDataSource(application)
    private val geocoder = Geocoder(application)

    private val coarsePermisionChecker =
        PermissionChecker(application, Manifest.permission.ACCESS_COARSE_LOCATION)

    suspend fun findRegion(): String = findLastLocation().toRegion()

    // Set default location when is false
    private suspend fun findLastLocation(): Location? {
        val success = coarsePermisionChecker.check()
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
