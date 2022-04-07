package com.godamy.myplayer.data

import com.godamy.myplayer.data.PermissionChecker.Permission.COARSE_LOCATION
import com.godamy.myplayer.data.datasource.LocationDataSource

class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    suspend fun findLastRegion(): String =
        if (permissionChecker.check(COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }

    companion object {
        private const val DEFAULT_REGION = "US"
    }
}
