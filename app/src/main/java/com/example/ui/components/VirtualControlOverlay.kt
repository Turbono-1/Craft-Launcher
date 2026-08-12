package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ControllerBinding
import com.example.data.model.ControlType
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkElevatedSurface
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.MinecraftGold
import com.example.ui.theme.MinecraftGreenPrimary
import kotlin.math.roundToInt

@Composable
fun VirtualControlOverlay(
    bindings: List<ControllerBinding>,
    onBindingMoved: (ControllerBinding, Float, Float) -> Unit,
    onBindingSelected: (ControllerBinding) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBinding by remember { mutableStateOf<ControllerBinding?>(bindings.firstOrNull()) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, DarkCardBorder, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TouchApp,
                        contentDescription = "Control Layout",
                        tint = DiamondCyan,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Touch Controls & Gamepad Studio",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gamepad,
                            contentDescription = "Gamepad",
                            tint = DiamondCyan,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Xbox/PS Controller Active",
                            style = MaterialTheme.typography.labelSmall,
                            color = DiamondCyan,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Simulated Game Screen Drag Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0A120B))
                    .border(1.dp, Color(0xFF1E3821), RoundedCornerShape(12.dp))
            ) {
                // Background HUD guide lines
                Text(
                    text = "DRAG BUTTONS TO CUSTOMIZE POSITIONS",
                    color = Color(0x33FFFFFF),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )

                // Render each interactive control element
                bindings.forEach { binding ->
                    var offsetX by remember(binding.id) { mutableFloatStateOf(binding.xPercent) }
                    var offsetY by remember(binding.id) { mutableFloatStateOf(binding.yPercent) }

                    val isSelected = selectedBinding?.id == binding.id

                    Box(
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    (offsetX * 3.0f).roundToInt(),
                                    (offsetY * 2.0f).roundToInt()
                                )
                            }
                            .size(if (binding.controlType == ControlType.TOUCH_JOYSTICK) 80.dp else binding.sizeDp.dp)
                            .clip(if (binding.controlType == ControlType.TOUCH_JOYSTICK) CircleShape else RoundedCornerShape(10.dp))
                            .background(
                                when {
                                    isSelected -> MinecraftGreenPrimary.copy(alpha = binding.opacityPercent / 100f)
                                    binding.buttonKey.contains("ATTACK") -> Color(0xFFFF5252).copy(alpha = binding.opacityPercent / 100f)
                                    binding.buttonKey.contains("USE") -> DiamondCyan.copy(alpha = binding.opacityPercent / 100f)
                                    else -> Color(0xFF263829).copy(alpha = binding.opacityPercent / 100f)
                                }
                            )
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) MinecraftGold else DarkCardBorder,
                                shape = if (binding.controlType == ControlType.TOUCH_JOYSTICK) CircleShape else RoundedCornerShape(10.dp)
                            )
                            .pointerInput(binding.id) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    offsetX = (offsetX + dragAmount.x / 3.0f).coerceIn(0f, 90f)
                                    offsetY = (offsetY + dragAmount.y / 2.0f).coerceIn(0f, 85f)
                                    onBindingMoved(binding, offsetX, offsetY)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = binding.displayName,
                            color = if (isSelected) Color.Black else Color.White,
                            fontSize = if (binding.controlType == ControlType.TOUCH_JOYSTICK) 10.sp else 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Control Details / Config Editor for selected element
            selectedBinding?.let { binding ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF111E13),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Configuring: ${binding.displayName}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MinecraftGreenPrimary
                            )
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Size: ${binding.sizeDp}dp",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.width(90.dp)
                            )
                            Slider(
                                value = binding.sizeDp.toFloat(),
                                onValueChange = {},
                                valueRange = 36f..120f,
                                colors = SliderDefaults.colors(
                                    thumbColor = MinecraftGreenPrimary,
                                    activeTrackColor = MinecraftGreenPrimary
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Opacity: ${binding.opacityPercent}%",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.width(90.dp)
                            )
                            Slider(
                                value = binding.opacityPercent.toFloat(),
                                onValueChange = {},
                                valueRange = 20f..100f,
                                colors = SliderDefaults.colors(
                                    thumbColor = DiamondCyan,
                                    activeTrackColor = DiamondCyan
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
