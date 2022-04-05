package com.godamy.myplayer.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.buildSpannedString
import com.godamy.myplayer.R
import com.godamy.myplayer.common.appendInfo
import com.godamy.myplayer.model.database.MediaItem

class DetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyAttr) {

    fun setDetailInfo(mediaItem: MediaItem) = with(mediaItem) {
        // Spanned String para agregar mas de una linea en el mismo TextView
        text = buildSpannedString {
            appendInfo(context, R.string.original_language, originalLanguage.uppercase())
            appendInfo(context, R.string.original_title, originalTitle)
            appendInfo(context, R.string.release_date, releaseDate)
            appendInfo(context, R.string.popularity, popularity.toString())
            appendInfo(context, R.string.vote_average, voteAverage.toString())
        }
    }
}
