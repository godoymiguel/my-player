package com.godamy.myplayer.model

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class RegionRepository(activity: AppCompatActivity) {

    // Inicializo el location service de google con el activity
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    private val geocoder = Geocoder(activity)

    private val coarsePermisionChecker =
        PermissionChecker(activity, Manifest.permission.ACCESS_COARSE_LOCATION)

    suspend fun findRegion(): String = findLastLocation().toRegion()

    // Set default location when is false
    private suspend fun findLastLocation(): Location? {
        val success = coarsePermisionChecker.request()
        return if (success) getRegionFromLocation() else null
    }

    @SuppressLint("MissingPermission")
    private suspend fun getRegionFromLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
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
