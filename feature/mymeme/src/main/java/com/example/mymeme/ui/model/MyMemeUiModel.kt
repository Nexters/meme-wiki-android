package com.example.mymeme.ui.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.mimu_bird.designsystem.R

data class MyMemeUiModel(
    val imageUrl: String,
    val title: String = "내 밈 편집하기"
)

data class DrawingTool(
    val strokeWidth: Width,
    val opacity: Float,
    val color: DrawingColor
)

//guiWidth: DrawingToolbar ui상 나와야하는 width, realWidth: 단계 값 1~5, canvasWidth: 캔버스의 path 실제 굵기
enum class Width(val guiWidth: Int, val realWidth: Int, val canvasWidth: Int) {
    LEVEL1(3, 1, 6),
    LEVEL2(6, 2, 13),
    LEVEL3(9, 3, 24),
    LEVEL4(12, 4, 32),
    LEVEL5(15, 5, 44)
}

enum class DrawingColor(
    val color: Color,
    val title: String
) {
    RED(Color(0xFFFF3634), "빨강"),
    ORANGE(Color(0xFFFFCB31), "주황"),
    YELLOW(Color(0xFFF7EB01), "노랑"),
    GREEN(Color(0xFF2DECD3), "초록"),
    BLUE(Color(0xFF3296FF), "파랑"),
    NAVY(Color(0xFF1147FE), "남색"),
    WHITE(Color.White, "보라"),
    BLACK(Color.Black, "검정")
}

enum class ColorCrayon(val painterResourceId: Int) {
    RED(R.drawable.crayon_red),
    ORANGE(R.drawable.crayon_orange),
    YELLOW(R.drawable.crayon_yellow),
    GREEN(R.drawable.crayon_green),
    BLUE(R.drawable.crayon_blue),
    NAVY(R.drawable.crayon_navy),
    WHITE(R.drawable.crayon_white),
    BLACK(R.drawable.crayon_black)
}

data class DrawingPath(
    val points: List<Offset>,
    val strokeWidth: Float,
    val opacity: Float,
    val color: DrawingColor
)

data class TextElement(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val position: Offset,
    val color: DrawingColor,
    val opacity: Float,
    val fontSize: Float = 24f
) 