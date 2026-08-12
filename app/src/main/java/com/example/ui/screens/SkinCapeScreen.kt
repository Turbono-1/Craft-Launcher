package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.model.Account
import com.example.data.model.ArmType
import com.example.data.model.CapePreset
import com.example.data.model.DefaultSkinsAndCapes
import com.example.data.model.SkinPreset
import com.example.ui.components.SkinPreviewView
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkElevatedSurface
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.MinecraftGold
import com.example.ui.theme.MinecraftGreenPrimary

@Composable
fun SkinCapeScreen(
    account: Account?,
    onApplySkinAndCape: (ArmType, String?, String?, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedArmType by remember(account) { mutableStateOf(account?.skinType ?: ArmType.STEVE) }
    var customSkinUrl by remember { mutableStateOf("") }
    var selectedSkinPreset by remember { mutableStateOf<SkinPreset?>(DefaultSkinsAndCapes.skinPresets.firstOrNull()) }
    var selectedCapePreset by remember { mutableStateOf<CapePreset?>(DefaultSkinsAndCapes.capePresets.getOrNull(1)) }

    val activeAccount = account ?: Account(username = "Guest")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Hero Banner graphic
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, DarkCardBorder, RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_skin_cape_banner_1786553166182),
                contentDescription = "Skin and Cape Banner",
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
                    text = "Client-Side Skin & Cape Studio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Personalized Minecraft avatar for multiplayer servers & singleplayer",
                    style = MaterialTheme.typography.bodySmall,
                    color = DiamondCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3D Canvas Preview
        SkinPreviewView(
            account = activeAccount.copy(
                skinType = selectedArmType,
                capeName = selectedCapePreset?.title ?: "None"
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Skin Selector Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkCardBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = "Skin",
                        tint = MinecraftGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Select Player Skin",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Model Arm Type
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Arm Model:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = selectedArmType == ArmType.STEVE,
                        onClick = { selectedArmType = ArmType.STEVE },
                        colors = RadioButtonDefaults.colors(selectedColor = MinecraftGreenPrimary)
                    )
                    Text("Steve (4px)", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)

                    Spacer(modifier = Modifier.width(12.dp))

                    RadioButton(
                        selected = selectedArmType == ArmType.ALEX,
                        onClick = { selectedArmType = ArmType.ALEX },
                        colors = RadioButtonDefaults.colors(selectedColor = MinecraftGreenPrimary)
                    )
                    Text("Alex (3px)", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Skin Presets Horizontal Gallery
                Text(
                    text = "Preset Skins Gallery:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(DefaultSkinsAndCapes.skinPresets) { preset ->
                        val isSelected = selectedSkinPreset?.id == preset.id
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0xFF1B3821) else Color(0xFF0F1A11),
                            modifier = Modifier
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MinecraftGreenPrimary else DarkCardBorder,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    selectedSkinPreset = preset
                                    selectedArmType = preset.armType
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = preset.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = if (isSelected) MinecraftGreenPrimary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = preset.armType.name,
                                    fontSize = 10.sp,
                                    color = DiamondCyan
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Custom PNG / URL Skin Input
                OutlinedTextField(
                    value = customSkinUrl,
                    onValueChange = { customSkinUrl = it },
                    label = { Text("Or Paste Custom Skin URL / NameMC Link") },
                    placeholder = { Text("https://textures.minecraft.net/texture/...") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MinecraftGreenPrimary,
                        unfocusedBorderColor = DarkCardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cape Selector Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkCardBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Cape",
                        tint = MinecraftGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Select Client-Side Cape",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(DefaultSkinsAndCapes.capePresets) { cape ->
                        val isSelected = selectedCapePreset?.id == cape.id
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0xFF382E0B) else Color(0xFF0F1A11),
                            modifier = Modifier
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MinecraftGold else DarkCardBorder,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedCapePreset = cape }
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = cape.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = if (isSelected) MinecraftGold else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = cape.rarity,
                                    fontSize = 10.sp,
                                    color = DiamondCyan
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Save & Apply Button
        Button(
            onClick = {
                val skinUrl = customSkinUrl.ifBlank { selectedSkinPreset?.previewUrl }
                val capeName = selectedCapePreset?.title
                onApplySkinAndCape(selectedArmType, skinUrl, capeName, null)
            },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MinecraftGreenPrimary,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Apply",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("APPLY SKIN & CAPE TO PLAYER", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}
