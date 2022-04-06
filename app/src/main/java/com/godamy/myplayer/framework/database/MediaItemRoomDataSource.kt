package com.godamy.myplayer.framework.database

import com.godamy.myplayer.data.datasource.MediaItemLocalDataSource
import com.godamy.myplayer.domain.MediaItem
import kotlinx.coroutines.flow.map

class MediaItemRoomDataSource(private val dao: MediaItemDao) : MediaItemLocalDataSource {

    override val mediaItems = dao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = dao.mediaItemCount() == 0

    override suspend fun save(mediaItems: List<MediaItem>) {
        dao.save(mediaItems.toDbEntity())
    }

    override suspend fun save(mediaItem: MediaItem) {
        dao.save(listOf(mediaItem.toDbEntity()))
    }

    override fun findById(id: Int) = dao.findById(id).map { it.toDomainModel() }

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
