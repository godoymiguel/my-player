package com.godamy.myplayer.ui.common

sealed class Filter {
    object None : Filter()
    class ByType(val video : Boolean) : Filter()
}