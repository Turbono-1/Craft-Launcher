package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ModCategory
import com.example.data.model.ModItem
import com.example.data.model.ModSource
import com.example.ui.components.ModCard
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkElevatedSurface
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.MinecraftGreenPrimary

@Composable
fun ModBrowserScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: ModCategory,
    onSelectCategory: (ModCategory) -> Unit,
    selectedSource: ModSource,
    onSelectSource: (ModSource) -> Unit,
    downloadedMods: List<ModItem>,
    onInstallMod: (ModItem) -> Unit,
    onRemoveMod: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Sample Modrinth & CurseForge search catalog
    val catalogMods = remember {
        listOf(
            ModItem(
                id = "mod_sodium",
                name = "Sodium",
                summary = "Modern rendering engine for Minecraft that greatly improves frame rates and micro-stuttering.",
                author = "JellySquid",
                category = ModCategory.MOD,
                source = ModSource.MODRINTH,
                iconUrl = "https://cdn.modrinth.com/data/AANobbA3/icon.png",
                downloadCount = 18_400_000L,
                rating = 4.9,
                supportedVersions = "1.20.4, 1.20.1",
                supportedLoaders = "Fabric, Quilt",
                fileName = "sodium-fabric-0.5.8+mc1.20.4.jar",
                fileSizeFormatted = "1.8 MB"
            ),
            ModItem(
                id = "mod_iris",
                name = "Iris Shaders",
                summary = "Modern shaderpack loader built for compatibility with Sodium and Optifine shaders.",
                author = "IrisTeam",
                category = ModCategory.MOD,
                source = ModSource.MODRINTH,
                iconUrl = "https://cdn.modrinth.com/data/YL57BKhM/icon.png",
                downloadCount = 12_100_000L,
                rating = 4.8,
                supportedVersions = "1.20.4, 1.20.1",
                supportedLoaders = "Fabric, Quilt",
                fileName = "iris-mc1.20.4-1.6.17.jar",
                fileSizeFormatted = "2.4 MB"
            ),
            ModItem(
                id = "modpack_rlcraft",
                name = "RLCraft Modpack",
                summary = "Extreme survival modpack with dragons, dungeons, RPG leveling, and temperature mechanics.",
                author = "Shivaxi",
                category = ModCategory.MODPACK,
                source = ModSource.CURSEFORGE,
                iconUrl = "https://media.forgecdn.net/avatars/223/454/637042079040997120.png",
                downloadCount = 28_500_000L,
                rating = 4.9,
                supportedVersions = "1.12.2",
                supportedLoaders = "Forge",
                fileName = "RLCraft-1.12.2-v2.9.3.mrpack",
                fileSizeFormatted = "320 MB"
            ),
            ModItem(
                id = "shader_bsl",
                name = "BSL Shaders",
                summary = "High performance realistic shader pack featuring volumetric fog, bloom, reflection, and ambient occlusion.",
                author = "CaptTatsu",
                category = ModCategory.SHADER,
                source = ModSource.MODRINTH,
                iconUrl = "https://cdn.modrinth.com/data/2391039/icon.png",
                downloadCount = 9_800_000L,
                rating = 4.9,
                supportedVersions = "1.16.5 - 1.21",
                supportedLoaders = "Iris, Optifine",
                fileName = "BSL_v8.2.09.zip",
                fileSizeFormatted = "14.2 MB"
            ),
            ModItem(
                id = "resource_faithful",
                name = "Faithful 32x",
                summary = "Enhanced HD default texture pack preserving Vanilla Minecraft aesthetics at 32x resolution.",
                author = "FaithfulTeam",
                category = ModCategory.RESOURCE_PACK,
                source = ModSource.CURSEFORGE,
                iconUrl = "https://media.forgecdn.net/avatars/102/391/6362910392.png",
                downloadCount = 15_200_000L,
                rating = 4.7,
                supportedVersions = "1.8 - 1.21",
                supportedLoaders = "Vanilla, All Loaders",
                fileName = "Faithful-32x-1.20.4.zip",
                fileSizeFormatted = "28.5 MB"
            ),
            ModItem(
                id = "mod_jei",
                name = "Just Enough Items (JEI)",
                summary = "Essential item and recipe viewer mod for viewing crafting recipes and modded items.",
                author = "mezz",
                category = ModCategory.MOD,
                source = ModSource.CURSEFORGE,
                iconUrl = "https://media.forgecdn.net/avatars/10/381/63581029.png",
                downloadCount = 210_000_000L,
                rating = 5.0,
                supportedVersions = "1.8 - 1.20.4",
                supportedLoaders = "Forge, Fabric, NeoForge",
                fileName = "jei-1.20.4-15.3.0.4.jar",
                fileSizeFormatted = "3.1 MB"
            )
        )
    }

    val installedIds = downloadedMods.map { it.id }.toSet()

    val filteredMods = catalogMods.filter { mod ->
        val matchesSource = mod.source == selectedSource
        val matchesCategory = mod.category == selectedCategory
        val matchesQuery = searchQuery.isBlank() ||
                mod.name.contains(searchQuery, ignoreCase = true) ||
                mod.summary.contains(searchQuery, ignoreCase = true)
        matchesSource && matchesCategory && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Hero Header Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, DarkCardBorder, RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_mods_banner_1786553154120),
                contentDescription = "Mods Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Modrinth & CurseForge Secure Downloader",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Search & 1-click install Mods, Modpacks, Shaders, and Resource Packs",
                    style = MaterialTheme.typography.bodySmall,
                    color = DiamondCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Mod Source Tabs (Modrinth vs CurseForge)
        TabRow(
            selectedTabIndex = if (selectedSource == ModSource.MODRINTH) 0 else 1,
            containerColor = DarkElevatedSurface,
            contentColor = MinecraftGreenPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[if (selectedSource == ModSource.MODRINTH) 0 else 1]),
                    color = if (selectedSource == ModSource.MODRINTH) Color(0xFF00AF5C) else Color(0xFFF16436)
                )
            },
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = selectedSource == ModSource.MODRINTH,
                onClick = { onSelectSource(ModSource.MODRINTH) },
                text = { Text("MODRINTH API", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedSource == ModSource.CURSEFORGE,
                onClick = { onSelectSource(ModSource.CURSEFORGE) },
                text = { Text("CURSEFORGE API", fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MinecraftGreenPrimary
                )
            },
            placeholder = { Text("Search Sodium, Iris, RLCraft, Shaders...") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MinecraftGreenPrimary,
                unfocusedBorderColor = DarkCardBorder,
                focusedContainerColor = DarkElevatedSurface,
                unfocusedContainerColor = DarkElevatedSurface
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Categories Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(ModCategory.entries.toTypedArray()) { cat ->
                val isSelected = selectedCategory == cat
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectCategory(cat) },
                    label = {
                        Text(
                            text = when (cat) {
                                ModCategory.MOD -> "Mods"
                                ModCategory.MODPACK -> "Modpacks"
                                ModCategory.SHADER -> "Shaders"
                                ModCategory.RESOURCE_PACK -> "Resource Packs"
                            },
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MinecraftGreenPrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Downloaded Mods Accordion Bar
        if (downloadedMods.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .border(1.dp, Color(0xFF233B27), RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF112214))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Installed Content (${downloadedMods.size})",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MinecraftGreenPrimary
                        )
                        Icon(
                            imageVector = Icons.Default.Extension,
                            contentDescription = "Installed",
                            tint = MinecraftGreenPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    downloadedMods.forEach { mod ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "• ${mod.name} (${mod.fileName})",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            IconButton(
                                onClick = { onRemoveMod(mod.id) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Search Results List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredMods) { mod ->
                val isInstalled = installedIds.contains(mod.id)
                ModCard(
                    mod = mod.copy(isInstalled = isInstalled),
                    onInstall = onInstallMod
                )
            }
        }
    }
}
