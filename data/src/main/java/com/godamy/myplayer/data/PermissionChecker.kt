package com.godamy.myplayer.data

interface PermissionChecker {
    enum class Permission { COARSE_LOCATION }

    fun check(permission: Permission): Boolean
}
