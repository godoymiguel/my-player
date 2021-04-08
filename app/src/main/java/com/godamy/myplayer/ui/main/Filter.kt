package com.godamy.myplayer.ui.main

import com.godamy.myplayer.model.MediaItem

sealed class Filter {
    object None : Filter ()
    class ByType(val video : Boolean) : Filter ()
}