package com.godamy.myplayer.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import com.godamy.myplayer.data.server.MockWebServerRule
import com.godamy.myplayer.data.server.fromJson
import com.godamy.myplayer.framework.database.MediaItemDao
import com.godamy.myplayer.ui.common.NavHostActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val locationPermissionChecker: GrantPermissionRule =
        GrantPermissionRule.grant("android.permission.ACCESS_COARSE_LOCATION")

    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Inject
    lateinit var mediaItemDao: MediaItemDao

    @Inject
    lateinit var remoteDataSource: MediaItemRemoteDataSource

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("popular_movies.json")
        )
        hiltRule.inject()
    }

    @Test
    fun test_it_works() = runTest {
        mediaItemDao.save(buildMediaItemEntity(1, 2, 3, 4, 5, 6))
        Assert.assertEquals(6, mediaItemDao.mediaItemCount())
    }

    @Test
    fun check_mock_server_is_working() = runTest {
        val movies = remoteDataSource.requestPopularMovies("ES")
        movies.fold({throw Exception(it.toString())}) {
            Assert.assertEquals("Morbius", it[0].title)
        }
    }
}