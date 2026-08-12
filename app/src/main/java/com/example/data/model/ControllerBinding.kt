package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

enum class ControlType {
    TOUCH_BUTTON,
    TOUCH_JOYSTICK,
    GAMEPAD_BUTTON,
    GAMEPAD_AXIS
}

@Entity(tableName = "controller_layouts")
data class ControllerBinding(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val layoutName: String = "Default Layout",
    val buttonKey: String, // e.g., "ATTACK", "USE", "JUMP", "SNEAK", "INVENTORY", "CHAT", "F3", "HOTBAR_1"
    val displayName: String,
    val controlType: ControlType = ControlType.TOUCH_BUTTON,
    val xPercent: Float, // 0.0 to 100.0 percent screen X
    val yPercent: Float, // 0.0 to 100.0 percent screen Y
    val sizeDp: Int = 54,
    val opacityPercent: Int = 80,
    val mappedKeycode: Int = 0, // Android KeyEvent keycode
    val isEnabled: Boolean = true
)

object DefaultControllerLayouts {
    val defaultButtons = listOf(
        ControllerBinding(buttonKey = "JOYSTICK", displayName = "Move D-Pad", controlType = ControlType.TOUCH_JOYSTICK, xPercent = 12f, yPercent = 70f, sizeDp = 120, opacityPercent = 75),
        ControllerBinding(buttonKey = "ATTACK", displayName = "L-CLICK / Attack", xPercent = 85f, yPercent = 52f, sizeDp = 64, opacityPercent = 85),
        ControllerBinding(buttonKey = "USE", displayName = "R-CLICK / Place", xPercent = 70f, yPercent = 64f, sizeDp = 58, opacityPercent = 85),
        ControllerBinding(buttonKey = "JUMP", displayName = "JUMP", xPercent = 88f, yPercent = 74f, sizeDp = 60, opacityPercent = 90),
        ControllerBinding(buttonKey = "SNEAK", displayName = "SNEAK", xPercent = 78f, yPercent = 84f, sizeDp = 50, opacityPercent = 80),
        ControllerBinding(buttonKey = "INVENTORY", displayName = "INV (E)", xPercent = 90f, yPercent = 25f, sizeDp = 48, opacityPercent = 80),
        ControllerBinding(buttonKey = "CHAT", displayName = "CHAT (T)", xPercent = 45f, yPercent = 8f, sizeDp = 44, opacityPercent = 70),
        ControllerBinding(buttonKey = "F3", displayName = "F3 DEBUG", xPercent = 58f, yPercent = 8f, sizeDp = 44, opacityPercent = 70),
        ControllerBinding(buttonKey = "F1", displayName = "F1 HUD", xPercent = 32f, yPercent = 8f, sizeDp = 44, opacityPercent = 70)
    )
}
