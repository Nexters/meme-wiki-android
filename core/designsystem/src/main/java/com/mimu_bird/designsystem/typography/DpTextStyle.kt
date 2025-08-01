package com.mimu_bird.designsystem.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toSp() = with(LocalDensity.current) { this@toSp.toSp() }

data class DpTextStyle(
    val fontSize: Dp = Dp.Unspecified,
    val fontWeight: FontWeight? = null,
    val fontFamily: FontFamily? = null,
    val letterSpacing: TextUnit = TextUnit.Unspecified,
    val lineHeight: Dp = Dp.Unspecified
)

@Composable
fun DpTextStyle.toTextStyle(): TextStyle {
    return TextStyle(
        fontSize = fontSize.toSp(),
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight.toSp()
    )
}