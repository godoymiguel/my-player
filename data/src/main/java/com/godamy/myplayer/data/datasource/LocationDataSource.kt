package com.godamy.myplayer.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
