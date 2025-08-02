package com.mimu_bird.designsystem.theme

import androidx.compose.ui.graphics.Color

enum class PastelGradientPalette(
    val leftTop: Color,
    val rightBottom: Color
) {
    PURPLE(
        leftTop = Color(0xFFF0ECFE),
        rightBottom = Color(0xFFC0B0FF)
    ),
    MAGENTA(
        leftTop = Color(0xFFFEECEC),
        rightBottom = Color(0xFFFFB5B5)
    ),
    LIGHT_BLUE(
        leftTop = Color(0xFFE5F6FE),
        rightBottom = Color(0xFFA1E1FF)
    ),
    GREEN(
        leftTop = Color(0xFFE6FFD4),
        rightBottom = Color(0xFFAEF779)
    ),
    YELLOW(
        leftTop = Color(0xFFFFF7BF),
        rightBottom = Color(0xFFFFE04B)
    ),
    PINK(
        leftTop = Color(0xFFFEECFB),
        rightBottom = Color(0xFFFFB8F3)
    ),
    BLUE(
        leftTop = Color(0xFFEAF2FE),
        rightBottom = Color(0xFF9EC5FF)
    ),
    ORANGE(
        leftTop = Color(0xFFFEEEE5),
        rightBottom = Color(0xFFFFBD96)
    )
}
