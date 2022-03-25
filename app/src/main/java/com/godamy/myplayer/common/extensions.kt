package com.godamy.myplayer.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast
        .makeText(this, message, length)
        .show()
}

fun RecyclerView.ViewHolder.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    itemView.context.toast(message, length)
}

fun ViewGroup.infate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(this.context).inflate(layoutRes, this, false)
}

fun ImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun SpannableStringBuilder.appendInfo(context: Context, stringRes: Int, value: String) {
    this.bold {
        append(context.getString(stringRes))
        append((": "))
    }
    this.appendLine(value)
}

// Start Activity
inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, Any?>) {
    /** Forma original
     * val bundle = bundleOf(*pairs)
     * val intent = Intent(this, T::class.java)
     * intent.putExtras(bundle)
     * startActivity(intent)
     * */
    Intent(this, T::class.java)
        .apply { putExtras(bundleOf(*pairs)) }
        .also(::startActivity)
}
