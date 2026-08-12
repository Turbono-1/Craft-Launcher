package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ControllerBinding
import kotlinx.coroutines.flow.Flow

@Dao
interface ControllerDao {
    @Query("SELECT * FROM controller_layouts WHERE layoutName = :layoutName")
    fun getLayoutBindings(layoutName: String = "Default Layout"): Flow<List<ControllerBinding>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBinding(binding: ControllerBinding)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBindings(bindings: List<ControllerBinding>)

    @Update
    suspend fun updateBinding(binding: ControllerBinding)

    @Query("DELETE FROM controller_layouts WHERE layoutName = :layoutName")
    suspend fun clearLayout(layoutName: String)
}
