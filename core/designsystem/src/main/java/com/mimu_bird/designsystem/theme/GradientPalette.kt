package com.mimu_bird.designsystem.theme

import androidx.compose.ui.graphics.Color

/**
 * 밈 Gradient 색상 팔레트
 */
enum class GradientPalette(
    val background: Color,
    val chip: Color
) {
    PURPLE(
        background = Color(0xFF7B00FF),
        chip = Color(0xFFF2D6FF)
    ),
    PINK(
        background = Color(0xFFD331B8),
        chip = Color(0xFFFED3F7)
    ),
    VIOLET(
        background = Color(0xFF3A16C9),
        chip = Color(0xFFDBD3FE)
    ),
    LIGHT_BLUE(
        background = Color(0xFF008ECF),
        chip = Color(0xFFC4ECFE)
    ),
    GREEN(
        background = Color(0xFF1ED45A),
        chip = Color(0xFFACFCC7)
    ),
    RED(
        background = Color(0xFFFF4242),
        chip = Color(0xFFFED5D5)
    ),
    YELLOW(
        background = Color(0xFFFEF08C),
        chip = Color(0xFFFEF08C)
    )
}

/**
 * 선형 그라디언트 색상 팔레트 (45도 대각선)
 */
enum class LinearGradientPalette(
    val leftTop: Color,
    val rightBottom: Color
) {
    PURPLE(
        leftTop = Color(0xFFF0ECFE),
        rightBottom = Color(0xFFC0B0FF)
    ),
    PINK(
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
    MAGENTA(
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
