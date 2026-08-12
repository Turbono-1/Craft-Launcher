package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.GameInstance
import kotlinx.coroutines.flow.Flow

@Dao
interface InstanceDao {
    @Query("SELECT * FROM game_instances ORDER BY lastPlayedTimestamp DESC")
    fun getAllInstances(): Flow<List<GameInstance>>

    @Query("SELECT * FROM game_instances WHERE isDefault = 1 LIMIT 1")
    fun getDefaultInstanceFlow(): Flow<GameInstance?>

    @Query("SELECT * FROM game_instances WHERE isDefault = 1 LIMIT 1")
    suspend fun getDefaultInstance(): GameInstance?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstance(instance: GameInstance)

    @Update
    suspend fun updateInstance(instance: GameInstance)

    @Query("UPDATE game_instances SET isDefault = 0")
    suspend fun clearDefaultInstances()

    @Query("DELETE FROM game_instances WHERE id = :id")
    suspend fun deleteInstanceById(id: String)
}
