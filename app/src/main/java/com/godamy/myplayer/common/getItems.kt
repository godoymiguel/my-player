package com.godamy.myplayer.common

import com.godamy.myplayer.common.MediaList.Type

data class MediaList(val title: String, val url: String, val type: Type) {
    enum class Type { PHOTO, VIDEO }
}

fun getItems() = (1 .. 10).map { MediaList(
    "Title $it",
    "https://placekitten.com/200/200?image=$it",
    if(it%3 == 0) Type.VIDEO else Type.PHOTO)
}