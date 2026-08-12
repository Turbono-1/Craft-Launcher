package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

enum class ArmType {
    STEVE, // Standard 4px arm
    ALEX   // Slim 3px arm
}

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val uuid: String = UUID.nameUUIDFromBytes("OfflinePlayer:$username".toByteArray()).toString(),
    val isSelected: Boolean = false,
    val skinUrl: String? = null,
    val skinType: ArmType = ArmType.STEVE,
    val capeUrl: String? = null,
    val capeName: String? = "None",
    val createdTimestamp: Long = System.currentTimeMillis(),
    val totalPlayTimeMinutes: Long = 0
)
