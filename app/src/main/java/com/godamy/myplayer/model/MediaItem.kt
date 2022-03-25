package com.godamy.myplayer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaItem(
    val adult: Boolean,
    @SerializedName("backdropPath") val backdrop_path: String,
    @SerializedName("genreIds") val genre_ids: List<Int>,
    val id: Int,
    @SerializedName("original_language")  val originalLanguage: String,
    @SerializedName("original_title")  val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")  val posterPath: String,
    @SerializedName("release_date")  val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")  val voteAverage: Double,
    @SerializedName("vote_count")  val voteCount: Int
) : Parcelable
