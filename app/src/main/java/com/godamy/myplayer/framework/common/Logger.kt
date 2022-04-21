package com.godamy.myplayer.framework.common

import android.util.Log

interface Logger {

    val tag: String
        get() = javaClass.simpleName

    fun logI(message: String) {
        Log.i(tag, message)
    }

    fun logE(message: String) {
        Log.e(tag, message)
    }
}
