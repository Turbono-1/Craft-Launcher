package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

enum class ModLoader {
    VANILLA,
    FABRIC,
    FORGE,
    NEOFORGE,
    QUILT
}

enum class JavaTargetVersion(val displayName: String, val majorVersion: Int) {
    JAVA_8("Java 8 (OpenJDK 8u382)", 8),
    JAVA_17("Java 17 (Temurin 17.0.9)", 17),
    JAVA_21("Java 21 (Temurin 21.0.1)", 21)
}

@Entity(tableName = "game_instances")
data class GameInstance(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val mcVersion: String, // e.g. "1.20.4", "1.20.1", "1.16.5", "1.8.9"
    val loader: ModLoader = ModLoader.FABRIC,
    val loaderVersion: String = "Latest",
    val javaVersion: JavaTargetVersion = JavaTargetVersion.JAVA_17,
    val allocatedRamMb: Int = 4096,
    val customJvmArgs: String = "-XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -Xmx4096M",
    val resolutionWidth: Int = 1280,
    val resolutionHeight: Int = 720,
    val isDefault: Boolean = false,
    val installedModsCount: Int = 0,
    val iconName: String = "grass_block",
    val lastPlayedTimestamp: Long = System.currentTimeMillis()
)
