package com.godamy.myplayer.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.godamy.myplayer.framework.database.MediaItemDao
import com.godamy.myplayer.ui.common.NavHostActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val locationPermissionChecker: GrantPermissionRule =
        GrantPermissionRule.grant("android.permission.ACCESS_COARSE_LOCATION")

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Inject
    lateinit var mediaItemDao: MediaItemDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_it_works() = runTest {
        mediaItemDao.save(buildMediaItemEntity(1,2,3,4,5,6))
        Assert.assertEquals(6, mediaItemDao.mediaItemCount())
    }
}