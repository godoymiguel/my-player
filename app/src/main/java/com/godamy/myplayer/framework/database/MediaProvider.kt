package com.godamy.myplayer.framework.database

import com.godamy.myplayer.framework.database.MediaList.Type

data class MediaList(val id: Int, val title: String, val url: String, val type: Type) {
    enum class Type { PHOTO, VIDEO }
}

// Use Objects
object MediaProvider {
    fun getItems(): List<MediaList> {
        return (1..10).map {
            MediaList(
                it,
                "Title $it",
                "https://placekitten.com/200/200?image=$it",
                if (it % 3 == 0) Type.VIDEO else Type.PHOTO
            )
        }
    }
}
