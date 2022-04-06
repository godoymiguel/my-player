package com.godamy.myplayer.framework.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaItemDao {

    @Query("SELECT * FROM MediaItemEntity")
    fun getAll(): Flow<List<MediaItemEntity>>

    @Query("SELECT * FROM MediaItemEntity WHERE ID = :id")
    fun findById(id: Int): Flow<MediaItemEntity>

    @Query("SELECT COUNT(id) FROM MediaItemEntity")
    suspend fun mediaItemCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(mediaItemEntity: List<MediaItemEntity>)
}
