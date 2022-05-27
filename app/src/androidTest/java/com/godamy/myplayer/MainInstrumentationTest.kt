package com.godamy.myplayer

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.godamy.myplayer.ui.common.NavHostActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainInstrumentationTest {

    @get:Rule
    val locationPermissionChecker: GrantPermissionRule =
        GrantPermissionRule.grant("android.permission.ACCESS_COARSE_LOCATION")

    @get:Rule
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Test
    fun test_it_works() {}
}