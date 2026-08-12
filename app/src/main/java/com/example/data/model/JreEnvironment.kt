package com.example.data.model

data class JreEnvironment(
    val version: JavaTargetVersion,
    val isInstalled: Boolean,
    val installPath: String,
    val arch: String = "ARM64 / AArch64",
    val memoryUsageMb: Int = 0,
    val recommendedForVersions: String
)

object DefaultJreEnvironments {
    val environments = listOf(
        JreEnvironment(
            version = JavaTargetVersion.JAVA_8,
            isInstalled = true,
            installPath = "/data/data/com.aistudio.craftlauncher/files/jre-8",
            arch = "aarch64",
            recommendedForVersions = "Minecraft 1.7.10 - 1.16.5 (Forge/Optifine)"
        ),
        JreEnvironment(
            version = JavaTargetVersion.JAVA_17,
            isInstalled = true,
            installPath = "/data/data/com.aistudio.craftlauncher/files/jre-17",
            arch = "aarch64",
            recommendedForVersions = "Minecraft 1.17 - 1.20.4 (Fabric/Forge/Quilt)"
        ),
        JreEnvironment(
            version = JavaTargetVersion.JAVA_21,
            isInstalled = true,
            installPath = "/data/data/com.aistudio.craftlauncher/files/jre-21",
            arch = "aarch64",
            recommendedForVersions = "Minecraft 1.20.5+ / 1.21 NeoForge & modern mods"
        )
    )
}
