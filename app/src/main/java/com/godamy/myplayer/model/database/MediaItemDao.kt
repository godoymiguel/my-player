package com.godamy.myplayer.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaItemDao {

    @Query("SELECT * FROM MediaItem")
    fun getAll(): Flow<List<MediaItem>>

    @Query("SELECT * FROM MediaItem WHERE ID = :id")
    fun findById(id: Int): Flow<MediaItem>

    @Query("SELECT COUNT(id) FROM MediaItem")
    suspend fun mediaItemCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(mediaItem: List<MediaItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(mediaItem: MediaItem)
}
