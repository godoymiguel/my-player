package com.godamy.myplayer.framework

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.godamy.myplayer.data.PermissionChecker
import com.godamy.myplayer.data.PermissionChecker.Permission.COARSE_LOCATION

class AndroidPermissionChecker(private val application: Application) : PermissionChecker {
    override fun check(permission: PermissionChecker.Permission): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission.toAndroidId()
        ) == PackageManager.PERMISSION_GRANTED

    private fun PermissionChecker.Permission.toAndroidId() = when (this) {
        COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION
    }
}
