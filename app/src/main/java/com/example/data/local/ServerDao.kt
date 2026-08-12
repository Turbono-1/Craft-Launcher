package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.McServer
import kotlinx.coroutines.flow.Flow

@Dao
interface ServerDao {
    @Query("SELECT * FROM mc_servers ORDER BY isFavorite DESC, name ASC")
    fun getAllServers(): Flow<List<McServer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServer(server: McServer)

    @Update
    suspend fun updateServer(server: McServer)

    @Query("DELETE FROM mc_servers WHERE id = :id")
    suspend fun deleteServerById(id: String)
}
