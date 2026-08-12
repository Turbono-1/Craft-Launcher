package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ModCategory {
    MOD,
    MODPACK,
    SHADER,
    RESOURCE_PACK
}

enum class ModSource {
    MODRINTH,
    CURSEFORGE
}

@Entity(tableName = "downloaded_mods")
data class ModItem(
    @PrimaryKey
    val id: String,
    val name: String,
    val summary: String,
    val author: String,
    val category: ModCategory,
    val source: ModSource,
    val iconUrl: String,
    val downloadCount: Long,
    val rating: Double,
    val supportedVersions: String, // e.g. "1.20.4, 1.20.1"
    val supportedLoaders: String,  // e.g. "Fabric, Forge"
    val fileName: String,
    val fileSizeFormatted: String,
    val isInstalled: Boolean = false,
    val targetInstanceId: String? = null,
    val downloadUrl: String = ""
)
