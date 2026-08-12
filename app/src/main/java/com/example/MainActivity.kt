package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ConsoleTerminalDialog
import com.example.ui.screens.AccountsScreen
import com.example.ui.screens.ControllerScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InstancesScreen
import com.example.ui.screens.JreManagerScreen
import com.example.ui.screens.ModBrowserScreen
import com.example.ui.screens.ServersScreen
import com.example.ui.screens.SkinCapeScreen
import com.example.ui.theme.CraftLauncherTheme
import com.example.ui.theme.DarkElevatedSurface
import com.example.ui.theme.DiamondCyan
import com.example.ui.theme.MinecraftGreenPrimary
import com.example.ui.viewmodel.MainViewModel

enum class NavDestination(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    INSTANCES("Instances", Icons.Default.Build),
    MODS("Mods Hub", Icons.Default.Extension),
    SKINS("Skins & Cape", Icons.Default.Palette),
    CONTROLS("Controls", Icons.Default.Gamepad),
    JRE("JRE Java", Icons.Default.Code),
    SERVERS("Servers", Icons.Default.Storage),
    ACCOUNTS("Accounts", Icons.Default.AccountCircle)
}

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CraftLauncherTheme {
                MainAppScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: MainViewModel) {
    var currentNav by remember { mutableStateOf(NavDestination.HOME) }

    val accounts by viewModel.accounts.collectAsStateWithLifecycle()
    val selectedAccount by viewModel.selectedAccount.collectAsStateWithLifecycle()

    val instances by viewModel.instances.collectAsStateWithLifecycle()
    val defaultInstance by viewModel.defaultInstance.collectAsStateWithLifecycle()

    val downloadedMods by viewModel.downloadedMods.collectAsStateWithLifecycle()
    val controllerBindings by viewModel.controllerBindings.collectAsStateWithLifecycle()
    val servers by viewModel.servers.collectAsStateWithLifecycle()
    val jreList by viewModel.jreList.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedModCategory.collectAsStateWithLifecycle()
    val selectedSource by viewModel.selectedModSource.collectAsStateWithLifecycle()

    val isConsoleOpen by viewModel.isConsoleOpen.collectAsStateWithLifecycle()
    val consoleLogs by viewModel.consoleLogs.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = DarkElevatedSurface,
                contentColor = MinecraftGreenPrimary,
                tonalElevation = 8.dp
            ) {
                NavDestination.entries.forEach { destination ->
                    val isSelected = currentNav == destination
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentNav = destination },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.title,
                                tint = if (isSelected) MinecraftGreenPrimary else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = destination.title,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MinecraftGreenPrimary else Color.Gray,
                                maxLines = 1
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color(0xFF1B3821)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        when (currentNav) {
            NavDestination.HOME -> {
                HomeScreen(
                    account = selectedAccount,
                    instance = defaultInstance,
                    servers = servers,
                    onLaunch = { inst, acc -> viewModel.launchGame(inst, acc) },
                    onNavigateToInstances = { currentNav = NavDestination.INSTANCES },
                    onNavigateToAccounts = { currentNav = NavDestination.ACCOUNTS },
                    onNavigateToSkins = { currentNav = NavDestination.SKINS },
                    modifier = modifier
                )
            }

            NavDestination.INSTANCES -> {
                InstancesScreen(
                    instances = instances,
                    selectedAccount = selectedAccount,
                    onPlayInstance = { inst, acc -> viewModel.launchGame(inst, acc) },
                    onSelectDefaultInstance = { viewModel.selectDefaultInstance(it) },
                    onSaveInstance = { viewModel.saveInstance(it) },
                    onDeleteInstance = { viewModel.deleteInstance(it) },
                    modifier = modifier
                )
            }

            NavDestination.MODS -> {
                ModBrowserScreen(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                    selectedCategory = selectedCategory,
                    onSelectCategory = { viewModel.selectModCategory(it) },
                    selectedSource = selectedSource,
                    onSelectSource = { viewModel.selectModSource(it) },
                    downloadedMods = downloadedMods,
                    onInstallMod = { viewModel.installMod(it) },
                    onRemoveMod = { viewModel.removeMod(it) },
                    modifier = modifier
                )
            }

            NavDestination.SKINS -> {
                SkinCapeScreen(
                    account = selectedAccount,
                    onApplySkinAndCape = { arm, skinUrl, capeName, capeUrl ->
                        viewModel.updateSkinAndCape(arm, skinUrl, capeName, capeUrl)
                    },
                    modifier = modifier
                )
            }

            NavDestination.CONTROLS -> {
                ControllerScreen(
                    bindings = controllerBindings,
                    onBindingMoved = { binding, x, y ->
                        viewModel.moveControllerBinding(binding, x, y)
                    },
                    onResetLayout = { viewModel.resetControllerLayout() },
                    modifier = modifier
                )
            }

            NavDestination.JRE -> {
                JreManagerScreen(
                    jreList = jreList,
                    modifier = modifier
                )
            }

            NavDestination.SERVERS -> {
                ServersScreen(
                    servers = servers,
                    selectedAccount = selectedAccount,
                    defaultInstance = defaultInstance,
                    onConnectServer = { srv, inst, acc ->
                        viewModel.launchGame(inst, acc)
                    },
                    onAddServer = { name, ip -> viewModel.addServer(name, ip) },
                    onToggleFavorite = { viewModel.toggleFavoriteServer(it) },
                    onDeleteServer = { viewModel.deleteServer(it) },
                    modifier = modifier
                )
            }

            NavDestination.ACCOUNTS -> {
                AccountsScreen(
                    accounts = accounts,
                    onSelectAccount = { viewModel.selectAccount(it) },
                    onCreateAccount = { user, arm -> viewModel.createAccount(user, arm) },
                    onDeleteAccount = { viewModel.deleteAccount(it) },
                    modifier = modifier
                )
            }
        }

        // JVM Boot Console Modal Dialog
        if (isConsoleOpen && defaultInstance != null && selectedAccount != null) {
            ConsoleTerminalDialog(
                instance = defaultInstance!!,
                account = selectedAccount!!,
                logLines = consoleLogs,
                onDismiss = { viewModel.closeConsole() },
                onKillGame = { viewModel.closeConsole() }
            )
        }
    }
}
