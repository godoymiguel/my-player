package com.godamy.myplayer.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.godamy.myplayer.R
import com.godamy.myplayer.common.Logger
import com.godamy.myplayer.databinding.ActivityMainBinding
import com.godamy.myplayer.model.MediaItem
import com.godamy.myplayer.model.apiservice.MovieDbClient
import com.godamy.myplayer.ui.detail.DetailActivity
import com.godamy.myplayer.ui.main.adapter.MediaItemAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity(), Logger {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val mediaItemAdapter = MediaItemAdapter(emptyList(), this::navigateTo)

    //TODO lateinit inicializo luego una variable
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    //TODO Pedir permiso
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            doRequestPopularMovies(isGranted)
        }
    //TODO con shouldShowRequestPermissionRationale puedo pedir por seguna ves el permiso
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            val message = when {
//                isGranted -> "Permission Granted"
//                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> "Should Show Rational"
//                else -> "Permission Rejected"
//            }
//            toast(message, Toast.LENGTH_LONG)
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO Inicializo el location service de google con el activity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mediaItemRecycler = binding.rvMain

        //TODO Llamado a los landas y funcion de extension

        mediaItemRecycler.adapter = mediaItemAdapter

        //TODO Solicitar permiso
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        logE("TEST ERROR LOG IN ACTIVITY")
    }

    //TODO Obtener localizacion con geocoder
    private fun getRegionFromLocation(location: Location?): String {
        if (location == null) {
            return DEFAULT_REGION
        }

        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1)

        //TODO el simbolo ?: se llama operador elvis
        return result.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

    private fun doRequestPopularMovies(isLocationGranted: Boolean) {
        //TODO Uso de corrutinas con funcion de suspencion
        lifecycleScope.launch(){
            val apiKey = getString(R.string.api_key)
            val region = getRegion(isLocationGranted)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey, region)
            mediaItemAdapter.items = popularMovies.results
            mediaItemAdapter.notifyDataSetChanged()
        }
    }

    //TODO hacer una funcion sincrona para corutina
    @SuppressLint("MissingPermission")
    private suspend fun getRegion(isLocationGranted: Boolean): String =
        suspendCancellableCoroutine { continuation ->
            if (isLocationGranted) {
                fusedLocationClient.lastLocation.addOnCompleteListener {
                    continuation.resume(getRegionFromLocation(it.result))
                }
            } else {
                continuation.resume(DEFAULT_REGION)
            }
        }

    private fun navigateTo(mediaItem: MediaItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, mediaItem)

        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        logD("Destroit")
    }
}