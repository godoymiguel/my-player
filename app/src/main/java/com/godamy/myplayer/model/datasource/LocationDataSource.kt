package com.godamy.myplayer.model.datasource

import android.location.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}
