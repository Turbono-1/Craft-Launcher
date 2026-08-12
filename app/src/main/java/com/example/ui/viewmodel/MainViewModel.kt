package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.AppRepository
import com.example.data.model.Account
import com.example.data.model.ArmType
import com.example.data.model.ControllerBinding
import com.example.data.model.DefaultJreEnvironments
import com.example.data.model.GameInstance
import com.example.data.model.JreEnvironment
import com.example.data.model.McServer
import com.example.data.model.ModCategory
import com.example.data.model.ModItem
import com.example.data.model.ModSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepository

    val accounts: StateFlow<List<Account>>
    val selectedAccount: StateFlow<Account?>

    val instances: StateFlow<List<GameInstance>>
    val defaultInstance: StateFlow<GameInstance?>

    val downloadedMods: StateFlow<List<ModItem>>
    val controllerBindings: StateFlow<List<ControllerBinding>>
    val servers: StateFlow<List<McServer>>

    val jreList: StateFlow<List<JreEnvironment>> = MutableStateFlow(DefaultJreEnvironments.environments)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedModCategory = MutableStateFlow(ModCategory.MOD)
    val selectedModCategory: StateFlow<ModCategory> = _selectedModCategory.asStateFlow()

    private val _selectedModSource = MutableStateFlow(ModSource.MODRINTH)
    val selectedModSource: StateFlow<ModSource> = _selectedModSource.asStateFlow()

    private val _isConsoleOpen = MutableStateFlow(false)
    val isConsoleOpen: StateFlow<Boolean> = _isConsoleOpen.asStateFlow()

    private val _consoleLogs = MutableStateFlow<List<String>>(emptyList())
    val consoleLogs: StateFlow<List<String>> = _consoleLogs.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AppRepository(database)

        accounts = repository.allAccounts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        selectedAccount = repository.selectedAccount.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

        instances = repository.allInstances.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        defaultInstance = repository.defaultInstance.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

        downloadedMods = repository.downloadedMods.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        controllerBindings = repository.controllerLayouts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        servers = repository.servers.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.initializeDefaults()
        }
    }

    // Account Actions
    fun createAccount(username: String, skinType: ArmType, capeName: String? = "Minecon 2011 Cape") {
        if (username.isBlank()) return
        viewModelScope.launch {
            repository.createOrSelectAccount(username.trim(), skinType, capeName = capeName)
        }
    }

    fun selectAccount(account: Account) {
        viewModelScope.launch {
            repository.selectAccount(account)
        }
    }

    fun updateSkinAndCape(skinType: ArmType, skinUrl: String?, capeName: String?, capeUrl: String? = null) {
        val currentAccount = selectedAccount.value ?: return
        viewModelScope.launch {
            repository.updateAccountSkinAndCape(currentAccount, skinType, skinUrl, capeName, capeUrl)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }

    // Instance Actions
    fun saveInstance(instance: GameInstance) {
        viewModelScope.launch {
            repository.saveInstance(instance)
        }
    }

    fun selectDefaultInstance(instance: GameInstance) {
        viewModelScope.launch {
            repository.selectDefaultInstance(instance)
        }
    }

    fun deleteInstance(instance: GameInstance) {
        viewModelScope.launch {
            repository.deleteInstance(instance)
        }
    }

    // Mod Actions
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectModCategory(category: ModCategory) {
        _selectedModCategory.value = category
    }

    fun selectModSource(source: ModSource) {
        _selectedModSource.value = source
    }

    fun installMod(mod: ModItem) {
        viewModelScope.launch {
            val targetInst = defaultInstance.value
            repository.installMod(mod.copy(targetInstanceId = targetInst?.id))
        }
    }

    fun removeMod(modId: String) {
        viewModelScope.launch {
            repository.removeMod(modId)
        }
    }

    // Controller Layout Actions
    fun moveControllerBinding(binding: ControllerBinding, newX: Float, newY: Float) {
        viewModelScope.launch {
            repository.saveControllerBinding(binding.copy(xPercent = newX, yPercent = newY))
        }
    }

    fun resetControllerLayout() {
        viewModelScope.launch {
            repository.resetDefaultControllerLayout()
        }
    }

    // Server Actions
    fun addServer(name: String, address: String) {
        if (name.isBlank() || address.isBlank()) return
        viewModelScope.launch {
            repository.addServer(McServer(name = name.trim(), address = address.trim()))
        }
    }

    fun toggleFavoriteServer(server: McServer) {
        viewModelScope.launch {
            repository.toggleFavoriteServer(server)
        }
    }

    fun deleteServer(server: McServer) {
        viewModelScope.launch {
            repository.deleteServer(server)
        }
    }

    // Game Launcher Execution Console Simulator
    fun launchGame(instance: GameInstance, account: Account) {
        _isConsoleOpen.value = true
        _consoleLogs.value = listOf(
            "[CRAFT_LAUNCHER] Initializing Java Runtime Environment...",
            "[JVM] Target JRE: ${instance.javaVersion.displayName}",
            "[JVM] Memory allocation: ${instance.allocatedRamMb} MB RAM (-Xmx${instance.allocatedRamMb}M)",
            "[JVM] Arguments: ${instance.customJvmArgs}",
            "[AUTH] Offline player session validated: Username='${account.username}', UUID='${account.uuid}'",
            "[CLIENT_SIDE] Injecting custom skin model: ${account.skinType} & Cape: '${account.capeName}'",
            "[MOD_MANAGER] Loading ${instance.installedModsCount} active mods from mods/ directory...",
            "[LWJGL] Setting up OpenGL 3.3 Core Profile graphics context...",
            "[GAME] Starting Minecraft ${instance.mcVersion} (${instance.loader.name})..."
        )

        viewModelScope.launch {
            delay(1200)
            addConsoleLog("[GAME/INFO] [Sodium] Rendering pipeline initialized.")
            delay(1000)
            addConsoleLog("[GAME/INFO] [Iris Shaders] Shaderpack engine ready.")
            delay(1200)
            addConsoleLog("[GAME/INFO] Sound engine started (OpenAL 1.1).")
            delay(1000)
            addConsoleLog("[GAME/INFO] Minecraft ${instance.mcVersion} loaded successfully! Joined main title screen.")
        }
    }

    private fun addConsoleLog(log: String) {
        _consoleLogs.value = _consoleLogs.value + log
    }

    fun closeConsole() {
        _isConsoleOpen.value = false
    }
}
