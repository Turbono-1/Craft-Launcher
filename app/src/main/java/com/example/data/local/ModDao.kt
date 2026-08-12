package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ModItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ModDao {
    @Query("SELECT * FROM downloaded_mods ORDER BY name ASC")
    fun getAllDownloadedMods(): Flow<List<ModItem>>

    @Query("SELECT * FROM downloaded_mods WHERE targetInstanceId = :instanceId")
    fun getModsForInstance(instanceId: String): Flow<List<ModItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMod(mod: ModItem)

    @Update
    suspend fun updateMod(mod: ModItem)

    @Query("DELETE FROM downloaded_mods WHERE id = :id")
    suspend fun deleteModById(id: String)
}
