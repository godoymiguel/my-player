package com.godamy.myplayer.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.godamy.myplayer.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)
    }
}
