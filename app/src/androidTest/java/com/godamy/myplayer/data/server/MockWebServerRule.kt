package com.godamy.myplayer.data.server

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule : TestWatcher() {

    lateinit var server: MockWebServer

    override fun starting(description: Description) {
        server = MockWebServer()
        super.starting(description)
    }

    override fun finished(description: Description) {
        server.shutdown()
    }
}