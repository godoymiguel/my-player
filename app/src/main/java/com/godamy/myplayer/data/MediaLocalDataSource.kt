package com.godamy.myplayer.data

import com.godamy.myplayer.data.database.MediaItemDao
import com.godamy.myplayer.data.database.MediaItemEntity
import com.godamy.myplayer.domain.MediaItem
import kotlinx.coroutines.flow.map

class MediaLocalDataSource(private val dao: MediaItemDao) {

    val mediaItems = dao.getAll().map { it.toDomainModel() }

    suspend fun isEmpty(): Boolean = dao.mediaItemCount() == 0

    suspend fun save(mediaItems: List<MediaItem>) {
        dao.save(mediaItems.toDbEntity())
    }

    suspend fun save(mediaItem: MediaItem) {
        dao.save(listOf(mediaItem.toDbEntity()))
    }

    fun findById(id: Int) = dao.findById(id).map { it.toDomainModel() }

    private fun List<MediaItemEntity>.toDomainModel(): List<MediaItem> = map { it.toDomainModel() }

    private fun MediaItemEntity.toDomainModel(): MediaItem = MediaItem(
        id,
        backdropPath,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        favorite
    )

    private fun List<MediaItem>.toDbEntity(): List<MediaItemEntity> = map { it.toDbEntity() }

    private fun MediaItem.toDbEntity(): MediaItemEntity = MediaItemEntity(
        id,
        backdropPath,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        favorite
    )
}
