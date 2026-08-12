package com.example.ui.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Account
import com.example.data.model.ArmType
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkElevatedSurface
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.MinecraftGold
import com.example.ui.theme.MinecraftGreenPrimary

@Composable
fun SkinPreviewView(
    account: Account,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, DarkCardBorder, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Player Avatar",
                        tint = MinecraftGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "3D Character Preview",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = if (account.skinType == ArmType.ALEX) "Slim (3px)" else "Steve (4px)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MinecraftGreenPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stylized 3D Minecraft Character Canvas Renderer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F1710))
                    .border(1.dp, Color(0xFF233827), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                val isSlim = account.skinType == ArmType.ALEX
                val hasCape = !account.capeName.isNullOrBlank() && account.capeName != "None"

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val centerX = size.width / 2f
                    val centerY = size.height / 2f - 10f

                    // Pedestal Platform shadow
                    drawOval(
                        color = Color(0x66000000),
                        topLeft = Offset(centerX - 60f, centerY + 85f),
                        size = Size(120f, 24f)
                    )
                    drawRoundRect(
                        color = Color(0xFF1B2E1E),
                        topLeft = Offset(centerX - 50f, centerY + 88f),
                        size = Size(100f, 12f),
                        cornerRadius = CornerRadius(6f, 6f)
                    )

                    // Draw Cape (flowing behind player if cape equipped)
                    if (hasCape) {
                        val capePath = Path().apply {
                            moveTo(centerX - 22f, centerY - 25f)
                            lineTo(centerX + 22f, centerY - 25f)
                            lineTo(centerX + 32f, centerY + 65f)
                            lineTo(centerX - 32f, centerY + 65f)
                            close()
                        }
                        // Cape background gradient style
                        drawPath(
                            path = capePath,
                            color = when {
                                account.capeName?.contains("2011") == true -> Color(0xFFB71C1C) // Red Minecon 2011
                                account.capeName?.contains("Optifine") == true -> Color(0xFF1565C0) // Blue Optifine
                                account.capeName?.contains("Mojang") == true -> Color(0xFF2E7D32) // Mojang Green
                                account.capeName?.contains("Cherry") == true -> Color(0xFFE91E63) // Cherry Blossom Pink
                                else -> Color(0xFF4A148C) // Purple
                            }
                        )
                        // Cape border outline
                        drawPath(
                            path = capePath,
                            color = Color(0xFFFFD700),
                            style = Stroke(width = 3f)
                        )
                    }

                    // Voxel Player Head
                    val headSize = 42f
                    val headX = centerX - headSize / 2f
                    val headY = centerY - 80f

                    // Head base
                    drawRoundRect(
                        color = Color(0xFFC49A6C), // Skin tone
                        topLeft = Offset(headX, headY),
                        size = Size(headSize, headSize),
                        cornerRadius = CornerRadius(4f, 4f)
                    )
                    // Hair block
                    drawRoundRect(
                        color = Color(0xFF4A2F13), // Brown hair
                        topLeft = Offset(headX - 2f, headY - 2f),
                        size = Size(headSize + 4f, 16f),
                        cornerRadius = CornerRadius(4f, 4f)
                    )
                    // Eyes (Cyan & White pixel eyes)
                    drawRect(
                        color = Color.White,
                        topLeft = Offset(headX + 8f, headY + 22f),
                        size = Size(8f, 6f)
                    )
                    drawRect(
                        color = Color(0xFF00B0FF),
                        topLeft = Offset(headX + 12f, headY + 22f),
                        size = Size(4f, 6f)
                    )
                    drawRect(
                        color = Color.White,
                        topLeft = Offset(headX + 26f, headY + 22f),
                        size = Size(8f, 6f)
                    )
                    drawRect(
                        color = Color(0xFF00B0FF),
                        topLeft = Offset(headX + 26f, headY + 22f),
                        size = Size(4f, 6f)
                    )

                    // Voxel Torso (Shirt)
                    val torsoW = 38f
                    val torsoH = 50f
                    val torsoX = centerX - torsoW / 2f
                    val torsoY = headY + headSize + 4f

                    val shirtColor = if (account.username.contains("Alex", ignoreCase = true) || account.skinType == ArmType.ALEX) Color(0xFF43A047) else Color(0xFF00ACC1)
                    drawRoundRect(
                        color = shirtColor,
                        topLeft = Offset(torsoX, torsoY),
                        size = Size(torsoW, torsoH),
                        cornerRadius = CornerRadius(3f, 3f)
                    )

                    // Voxel Arms
                    val armW = if (isSlim) 10f else 12f
                    val armH = 48f
                    // Left Arm
                    drawRoundRect(
                        color = shirtColor,
                        topLeft = Offset(torsoX - armW - 2f, torsoY),
                        size = Size(armW, armH),
                        cornerRadius = CornerRadius(3f, 3f)
                    )
                    // Right Arm
                    drawRoundRect(
                        color = shirtColor,
                        topLeft = Offset(torsoX + torsoW + 2f, torsoY),
                        size = Size(armW, armH),
                        cornerRadius = CornerRadius(3f, 3f)
                    )

                    // Voxel Legs (Pants)
                    val legW = 18f
                    val legH = 46f
                    val legY = torsoY + torsoH + 2f
                    val pantsColor = Color(0xFF283593) // Indigo pants
                    // Left Leg
                    drawRoundRect(
                        color = pantsColor,
                        topLeft = Offset(centerX - legW - 1f, legY),
                        size = Size(legW, legH),
                        cornerRadius = CornerRadius(2f, 2f)
                    )
                    // Right Leg
                    drawRoundRect(
                        color = pantsColor,
                        topLeft = Offset(centerX + 1f, legY),
                        size = Size(legW, legH),
                        cornerRadius = CornerRadius(2f, 2f)
                    )
                }

                // Overlay Info Badge
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .background(Color(0xCC000000), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Ready",
                        tint = MinecraftGreenPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = account.username,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                if (hasCape) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                            .background(Color(0xCC000000), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Cape",
                            tint = MinecraftGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = account.capeName ?: "Cape",
                            color = DiamondCyan,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
