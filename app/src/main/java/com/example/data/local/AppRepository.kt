package com.example.data.local

import com.example.data.model.Account
import com.example.data.model.ArmType
import com.example.data.model.ControllerBinding
import com.example.data.model.DefaultControllerLayouts
import com.example.data.model.DefaultMcServers
import com.example.data.model.GameInstance
import com.example.data.model.JavaTargetVersion
import com.example.data.model.McServer
import com.example.data.model.ModItem
import com.example.data.model.ModLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AppRepository(private val db: AppDatabase) {

    val allAccounts: Flow<List<Account>> = db.accountDao().getAllAccounts()
    val selectedAccount: Flow<Account?> = db.accountDao().getSelectedAccountFlow()

    val allInstances: Flow<List<GameInstance>> = db.instanceDao().getAllInstances()
    val defaultInstance: Flow<GameInstance?> = db.instanceDao().getDefaultInstanceFlow()

    val downloadedMods: Flow<List<ModItem>> = db.modDao().getAllDownloadedMods()
    val controllerLayouts: Flow<List<ControllerBinding>> = db.controllerDao().getLayoutBindings("Default Layout")
    val servers: Flow<List<McServer>> = db.serverDao().getAllServers()

    suspend fun initializeDefaults() {
        // Seed default account if none exists
        val currentAccounts = allAccounts.firstOrNull() ?: emptyList()
        if (currentAccounts.isEmpty()) {
            val defaultAccount = Account(
                username = "CraftPlayer",
                isSelected = true,
                skinType = ArmType.STEVE,
                capeName = "Minecon 2011 Cape"
            )
            db.accountDao().insertAccount(defaultAccount)
        }

        // Seed default instances if none exists
        val currentInstances = allInstances.firstOrNull() ?: emptyList()
        if (currentInstances.isEmpty()) {
            val defaultInstance = GameInstance(
                name = "Fabric 1.20.4 (Performance & Iris Shaders)",
                mcVersion = "1.20.4",
                loader = ModLoader.FABRIC,
                javaVersion = JavaTargetVersion.JAVA_17,
                allocatedRamMb = 4096,
                isDefault = true,
                installedModsCount = 4
            )
            val forgeInstance = GameInstance(
                name = "Forge 1.20.1 (Modpack Ready)",
                mcVersion = "1.20.1",
                loader = ModLoader.FORGE,
                javaVersion = JavaTargetVersion.JAVA_17,
                allocatedRamMb = 6144,
                isDefault = false,
                installedModsCount = 12
            )
            val vanillaInstance = GameInstance(
                name = "Vanilla 1.21.1 (Trident & Trial Chambers)",
                mcVersion = "1.21.1",
                loader = ModLoader.VANILLA,
                javaVersion = JavaTargetVersion.JAVA_21,
                allocatedRamMb = 3072,
                isDefault = false,
                installedModsCount = 0
            )
            db.instanceDao().insertInstance(defaultInstance)
            db.instanceDao().insertInstance(forgeInstance)
            db.instanceDao().insertInstance(vanillaInstance)
        }

        // Seed default controller bindings
        val currentControls = controllerLayouts.firstOrNull() ?: emptyList()
        if (currentControls.isEmpty()) {
            db.controllerDao().insertBindings(DefaultControllerLayouts.defaultButtons)
        }

        // Seed sample servers
        val currentServers = servers.firstOrNull() ?: emptyList()
        if (currentServers.isEmpty()) {
            DefaultMcServers.sampleServers.forEach { server ->
                db.serverDao().insertServer(server)
            }
        }
    }

    // Account functions
    suspend fun createOrSelectAccount(username: String, skinType: ArmType, skinUrl: String? = null, capeName: String? = null) {
        db.accountDao().clearSelectedAccounts()
        val account = Account(
            username = username,
            isSelected = true,
            skinType = skinType,
            skinUrl = skinUrl,
            capeName = capeName ?: "Minecon 2011 Cape"
        )
        db.accountDao().insertAccount(account)
    }

    suspend fun selectAccount(account: Account) {
        db.accountDao().clearSelectedAccounts()
        db.accountDao().updateAccount(account.copy(isSelected = true))
    }

    suspend fun updateAccountSkinAndCape(account: Account, skinType: ArmType, skinUrl: String?, capeName: String?, capeUrl: String? = null) {
        val updated = account.copy(
            skinType = skinType,
            skinUrl = skinUrl ?: account.skinUrl,
            capeName = capeName ?: account.capeName,
            capeUrl = capeUrl ?: account.capeUrl
        )
        db.accountDao().updateAccount(updated)
    }

    suspend fun deleteAccount(account: Account) {
        db.accountDao().deleteAccountById(account.id)
    }

    // Instance functions
    suspend fun saveInstance(instance: GameInstance) {
        if (instance.isDefault) {
            db.instanceDao().clearDefaultInstances()
        }
        db.instanceDao().insertInstance(instance)
    }

    suspend fun selectDefaultInstance(instance: GameInstance) {
        db.instanceDao().clearDefaultInstances()
        db.instanceDao().updateInstance(instance.copy(isDefault = true, lastPlayedTimestamp = System.currentTimeMillis()))
    }

    suspend fun deleteInstance(instance: GameInstance) {
        db.instanceDao().deleteInstanceById(instance.id)
    }

    // Mod functions
    suspend fun installMod(mod: ModItem) {
        db.modDao().insertMod(mod.copy(isInstalled = true))
    }

    suspend fun removeMod(modId: String) {
        db.modDao().deleteModById(modId)
    }

    // Controller Layout functions
    suspend fun saveControllerBinding(binding: ControllerBinding) {
        db.controllerDao().insertBinding(binding)
    }

    suspend fun resetDefaultControllerLayout() {
        db.controllerDao().clearLayout("Default Layout")
        db.controllerDao().insertBindings(DefaultControllerLayouts.defaultButtons)
    }

    // Server functions
    suspend fun addServer(server: McServer) {
        db.serverDao().insertServer(server)
    }

    suspend fun toggleFavoriteServer(server: McServer) {
        db.serverDao().updateServer(server.copy(isFavorite = !server.isFavorite))
    }

    suspend fun deleteServer(server: McServer) {
        db.serverDao().deleteServerById(server.id)
    }
}
