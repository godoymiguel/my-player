package com.godamy.myplayer.model

import android.location.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}
