package com.example.data.model

data class SkinPreset(
    val id: String,
    val title: String,
    val previewUrl: String,
    val armType: ArmType,
    val description: String
)

data class CapePreset(
    val id: String,
    val title: String,
    val previewUrl: String,
    val rarity: String,
    val category: String // Minecon, Optifine, Special
)

object DefaultSkinsAndCapes {
    val skinPresets = listOf(
        SkinPreset("skin_steve", "Classic Steve", "https://textures.minecraft.net/texture/1a4223f6d71d388339c09c2514d334585141071d07bc2990d0b04bc615d6d84f", ArmType.STEVE, "Standard default Minecraft player skin"),
        SkinPreset("skin_alex", "Classic Alex", "https://textures.minecraft.net/texture/a6b25110d1f70514ca03d36b7a5a3a2d59302685a737f53a479a4192b00", ArmType.ALEX, "Slim-armed default Minecraft player skin"),
        SkinPreset("skin_diamond_armor", "Diamond Knight", "https://textures.minecraft.net/texture/c3d314f8a329d2f23293029193", ArmType.STEVE, "Full enchanted diamond armor battle suit"),
        SkinPreset("skin_nether_lord", "Nether Lord", "https://textures.minecraft.net/texture/8391283921382913", ArmType.STEVE, "Fiery nether warrior with glowing eyes"),
        SkinPreset("skin_cyber_steve", "Cyber Neon Steve", "https://textures.minecraft.net/texture/9391032139120", ArmType.ALEX, "Futuristic cybernetic explorer skin")
    )

    val capePresets = listOf(
        CapePreset("cape_none", "No Cape", "", "Common", "Default"),
        CapePreset("cape_minecon_2011", "Minecon 2011 Cape", "https://textures.minecraft.net/texture/8a23071391203", "Legendary", "Minecon"),
        CapePreset("cape_minecon_2013", "Minecon 2013 Piston", "https://textures.minecraft.net/texture/39102391039", "Epic", "Minecon"),
        CapePreset("cape_minecon_2015", "Minecon 2015 Golem", "https://textures.minecraft.net/texture/3012930120", "Epic", "Minecon"),
        CapePreset("cape_optifine_white", "Optifine White Banner", "https://textures.minecraft.net/texture/39021931029", "Rare", "Optifine"),
        CapePreset("cape_mojang_studios", "Mojang Studios Cape", "https://textures.minecraft.net/texture/931023910", "Legendary", "Official"),
        CapePreset("cape_cherry_blossom", "15th Anniversary Cape", "https://textures.minecraft.net/texture/391029310", "Special", "Event")
    )
}
