package com.godamy.myplayer.data

import com.godamy.myplayer.data.PermissionChecker.Permission.COARSE_LOCATION
import com.godamy.myplayer.data.datasource.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Test
    fun `Return default region with coarse permission not granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn false }
        )

        val region = regionRepository.findLastRegion()

        assertEquals(RegionRepository.DEFAULT_REGION, region)
    }

    @Test
    fun `Return region from local data source when permission granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            locationDataSource = mock { onBlocking { findLastRegion() } doReturn "ES" },
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn true }
        )

        val region = regionRepository.findLastRegion()

        assertEquals("ES", region)
    }

    private fun buildRegionRepository(
        locationDataSource: LocationDataSource = mock(),
        permissionChecker: PermissionChecker = mock()
    ) = RegionRepository(locationDataSource, permissionChecker)
}
