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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.godamy.myplayer.databinding.FragmentDetailBinding
import com.godamy.myplayer.ui.detail.DetailUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast
        .makeText(this, message, length)
        .show()
}

fun RecyclerView.ViewHolder.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    itemView.context.toast(message, length)
}

fun ViewGroup.infate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(this.context).inflate(layoutRes, this, false)

fun ImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun SpannableStringBuilder.appendInfo(context: Context, stringRes: Int, value: String) {
    this.bold {
        append(context.getString(stringRes))
        append(": ")
    }
    this.appendLine(value)
}

// e.g. update with function
fun FragmentDetailBinding.updateUI(state: DetailUiState) {
    state.mediaItem.let {
        toolbar.title = it.title
        val background = it.backdropPath ?: it.posterPath
        ivBackdropPath.loadUrl("https://image.tmdb.org/t/p/w780/$background")
        tvDetailSummary.text = it.overview
        tvDetailInfo.setDetailInfo(it)
    }
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

inline fun <T> basicDiffUtil(
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}

// lifecycle
fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}
