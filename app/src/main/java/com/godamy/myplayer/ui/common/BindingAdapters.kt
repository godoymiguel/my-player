package com.godamy.myplayer.ui.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.godamy.myplayer.framework.common.loadUrl

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    url?.let(this::loadUrl)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}
