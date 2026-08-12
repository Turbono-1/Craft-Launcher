package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "mc_servers")
data class McServer(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val address: String, // e.g., "mc.hypixel.net"
    val port: Int = 25565,
    val motd: String = "A Minecraft Java Edition Server",
    val pingMs: Int = 32,
    val onlinePlayers: Int = 14200,
    val maxPlayers: Int = 20000,
    val isOnline: Boolean = true,
    val supportedVersion: String = "1.8 - 1.20.4",
    val isFavorite: Boolean = false
)

object DefaultMcServers {
    val sampleServers = listOf(
        McServer(name = "Hypixel Network", address = "mc.hypixel.net", motd = "§e§lHYPIXEL NETWORK §7[1.8-1.20.4]\n§bBedWars, SkyBlock & Murder Mystery!", pingMs = 28, onlinePlayers = 48510, maxPlayers = 100000, isFavorite = true),
        McServer(name = "Complex Gaming", address = "hub.mc-complex.com", motd = "§c§lCOMPLEX GAMING §f- Pixelmon, Skyblock & Factions", pingMs = 45, onlinePlayers = 3120, maxPlayers = 5000, isFavorite = true),
        McServer(name = "ManaCube", address = "play.manacube.com", motd = "§a§lMANACUBE §7- Parkour, Survival & Earth RPG", pingMs = 52, onlinePlayers = 1890, maxPlayers = 3500),
        McServer(name = "CubeCraft Games", address = "play.cubecraft.net", motd = "§b§lCUBECRAFT §7- EggWars, Lucky Islands & Parkour", pingMs = 38, onlinePlayers = 2410, maxPlayers = 10000)
    )
}
