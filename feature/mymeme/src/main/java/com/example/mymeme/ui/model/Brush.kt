package com.example.mymeme.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mimu_bird.mymeme.R

enum class BrushColor (
    val color: Color,
    @DrawableRes val pen: Int
){
    RED(
        Color(0xFFFF3634),
        com.mimu_bird.designsystem.R.drawable.crayon_red
    ),
    ORANGE(
        Color(0xFFFFCB31),
        com.mimu_bird.designsystem.R.drawable.crayon_orange
    ),
    YELLOW(
        Color((0xFFFFF300)),
        com.mimu_bird.designsystem.R.drawable.crayon_yellow
    ),
    MINT(
        Color(0xFF2DECD3),
        com.mimu_bird.designsystem.R.drawable.crayon_green
    ),
    LIGHT_BLUE(
        Color(0xFF3296FF),
        com.mimu_bird.designsystem.R.drawable.crayon_blue
    ),
    BLUE(
        Color(0xFF1147FE),
        com.mimu_bird.designsystem.R.drawable.crayon_navy
    ),
    WHITE(
        Color.White,
        com.mimu_bird.designsystem.R.drawable.crayon_white
    ),
    BLACK(
        Color.Black,
        com.mimu_bird.designsystem.R.drawable.crayon_black
    )
}
enum class BrushWidth (
    val width: Dp,
    val selectorWidth: Dp,
    @DrawableRes val pen: Int
){
    ONE(
        3.dp,
        4.dp,
        com.mimu_bird.designsystem.R.drawable.ic_width1
    ),
    TWO(
        6.dp,
        6.dp,
        com.mimu_bird.designsystem.R.drawable.ic_width2
    ),
    THREE(
        9.dp,
        8.dp,
        com.mimu_bird.designsystem.R.drawable.ic_width3
    ),
    FOUR(
        12.dp,
        10.dp,
        com.mimu_bird.designsystem.R.drawable.ic_width4
    ),
    FIVE(
        16.dp,
        12.dp,
        com.mimu_bird.designsystem.R.drawable.ic_width5
    )
}

data class Brush(
    val color: BrushColor,
    val width: BrushWidth,
    val alpha: Float
) {
    val drawingColor = color.color.copy(alpha)
    val drawingWidth = width.width

    companion object {
        val DEFAULT = Brush(
            color = BrushColor.WHITE,
            width = BrushWidth.THREE,
            alpha = 0.5f
        )
    }
}
