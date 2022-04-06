package com.godamy.myplayer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val backdropPath: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val favorite: Boolean
)
