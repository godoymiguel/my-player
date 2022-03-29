package com.godamy.myplayer.model

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServiceLocationDataSource(activity: Activity) : LocationDataSource {

    // Inicializo el location service de google con el activity
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }
}
