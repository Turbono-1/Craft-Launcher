package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Account
import com.example.data.model.GameInstance
import com.example.data.model.JavaTargetVersion
import com.example.data.model.ModLoader
import com.example.ui.components.InstanceCard
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkElevatedSurface
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.MinecraftGreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstancesScreen(
    instances: List<GameInstance>,
    selectedAccount: Account?,
    onPlayInstance: (GameInstance, Account) -> Unit,
    onSelectDefaultInstance: (GameInstance) -> Unit,
    onSaveInstance: (GameInstance) -> Unit,
    onDeleteInstance: (GameInstance) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCreateForm by remember { mutableStateOf(false) }

    var instanceNameInput by remember { mutableStateOf("") }
    var selectedMcVersion by remember { mutableStateOf("1.20.4") }
    var selectedLoader by remember { mutableStateOf(ModLoader.FABRIC) }
    var selectedJre by remember { mutableStateOf(JavaTargetVersion.JAVA_17) }
    var allocatedRamMb by remember { mutableFloatStateOf(4096f) }

    val mcVersions = listOf("1.21.1", "1.20.4", "1.20.1", "1.19.4", "1.16.5", "1.12.2", "1.8.9")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Minecraft Game Instances",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Button(
                onClick = { showCreateForm = !showCreateForm },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (showCreateForm) DarkElevatedSurface else MinecraftGreenPrimary,
                    contentColor = if (showCreateForm) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = if (showCreateForm) Icons.Default.Build else Icons.Default.Add,
                    contentDescription = "New Instance",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (showCreateForm) "Hide Builder" else "New Instance", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (showCreateForm) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(1.dp, MinecraftGreenPrimary, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Build Custom Game Instance",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MinecraftGreenPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = instanceNameInput,
                        onValueChange = { instanceNameInput = it },
                        label = { Text("Instance Name") },
                        placeholder = { Text("e.g. Fabric 1.20.4 Performance Pack") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MinecraftGreenPrimary,
                            unfocusedBorderColor = DarkCardBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // MC Version Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Game Version:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            var expanded by remember { mutableStateOf(false) }
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = it }
                            ) {
                                OutlinedTextField(
                                    value = selectedMcVersion,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MinecraftGreenPrimary,
                                        unfocusedBorderColor = DarkCardBorder
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    mcVersions.forEach { ver ->
                                        DropdownMenuItem(
                                            text = { Text(ver) },
                                            onClick = {
                                                selectedMcVersion = ver
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Mod Loader:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            var expandedLoader by remember { mutableStateOf(false) }
                            ExposedDropdownMenuBox(
                                expanded = expandedLoader,
                                onExpandedChange = { expandedLoader = it }
                            ) {
                                OutlinedTextField(
                                    value = selectedLoader.name,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLoader) },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MinecraftGreenPrimary,
                                        unfocusedBorderColor = DarkCardBorder
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedLoader,
                                    onDismissRequest = { expandedLoader = false }
                                ) {
                                    ModLoader.entries.forEach { loader ->
                                        DropdownMenuItem(
                                            text = { Text(loader.name) },
                                            onClick = {
                                                selectedLoader = loader
                                                expandedLoader = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // RAM Allocation Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "RAM Allocation:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${allocatedRamMb.toInt()} MB (${allocatedRamMb.toInt() / 1024} GB)",
                            fontWeight = FontWeight.Bold,
                            color = DiamondCyan,
                            fontSize = 13.sp
                        )
                    }

                    Slider(
                        value = allocatedRamMb,
                        onValueChange = { allocatedRamMb = it },
                        valueRange = 1024f..12288f,
                        steps = 10,
                        colors = SliderDefaults.colors(
                            thumbColor = MinecraftGreenPrimary,
                            activeTrackColor = MinecraftGreenPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val name = instanceNameInput.ifBlank { "${selectedLoader.name} $selectedMcVersion" }
                            val newInst = GameInstance(
                                name = name,
                                mcVersion = selectedMcVersion,
                                loader = selectedLoader,
                                javaVersion = if (selectedMcVersion >= "1.20.5") JavaTargetVersion.JAVA_21 else JavaTargetVersion.JAVA_17,
                                allocatedRamMb = allocatedRamMb.toInt(),
                                isDefault = instances.isEmpty()
                            )
                            onSaveInstance(newInst)
                            instanceNameInput = ""
                            showCreateForm = false
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MinecraftGreenPrimary,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("SAVE & CREATE INSTANCE", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(instances) { instance ->
                InstanceCard(
                    instance = instance,
                    onPlay = { inst ->
                        val currentAcc = selectedAccount ?: Account(username = "GuestPlayer")
                        onPlayInstance(inst, currentAcc)
                    },
                    onSelectDefault = onSelectDefaultInstance,
                    onDelete = onDeleteInstance
                )
            }
        }
    }
}
