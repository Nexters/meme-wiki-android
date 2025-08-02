package com.mimu_bird.designsystem.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.typography.DpTextStyle

private val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard)
)

private val pretendardTextStyle = DpTextStyle(
    fontFamily = pretendardFontFamily
)

// Title
// Display
val Display5 = pretendardTextStyle.copy(
    fontSize = 40.dp,
    fontWeight = FontWeight.Bold,
    letterSpacing = (-0.006).em,
    lineHeight = 52.dp
)
val Display4 = pretendardTextStyle.copy(
    fontSize = 36.dp,
    fontWeight = FontWeight.Bold,
    letterSpacing = (-0.006).em,
    lineHeight = 46.dp
)
val Display3 = pretendardTextStyle.copy(
    fontSize = 32.dp,
    fontWeight = FontWeight.Bold,
    letterSpacing = (-0.006).em,
    lineHeight = 42.dp
)
val Display2 = pretendardTextStyle.copy(
    fontSize = 28.dp,
    fontWeight = FontWeight.Bold,
    letterSpacing = (-0.006).em,
    lineHeight = 38.dp
)
val Display1 = pretendardTextStyle.copy(
    fontSize = 24.dp,
    fontWeight = FontWeight.Bold,
    letterSpacing = (-0.006).em,
    lineHeight = 34.dp
)
val Headline1 = pretendardTextStyle.copy(
    fontSize = 20.dp,
    fontWeight = FontWeight.Bold,
    letterSpacing = (-0.006).em,
    lineHeight = 28.dp
)
val Headline2 = pretendardTextStyle.copy(
    fontSize = 18.dp,
    fontWeight = FontWeight.Bold,
    letterSpacing = (-0.006).em,
    lineHeight = 24.dp
)
val Subhead1 = pretendardTextStyle.copy(
    fontSize = 16.dp,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = (-0.006).em,
    lineHeight = 22.dp
)
val Subhead_Long1 = pretendardTextStyle.copy(
    fontSize = 16.dp,
    fontWeight = FontWeight.Bold,
    letterSpacing = (-0.006).em,
    lineHeight = 28.dp
)
val Subhead2 = pretendardTextStyle.copy(
    fontSize = 14.dp,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = (-0.006).em,
    lineHeight = 20.dp
)
val Subhead_Long2 = pretendardTextStyle.copy(
    fontSize = 14.dp,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = (-0.006).em,
    lineHeight = 24.dp
)

// Body
val Body2 = pretendardTextStyle.copy(
    fontSize = 16.dp,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.006).em,
    lineHeight = 24.dp
)
val Body_Long2 =pretendardTextStyle.copy(
    fontSize = 16.dp,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.006).em,
    lineHeight = 28.dp
)
val Body1 = pretendardTextStyle.copy(
    fontSize = 14.dp,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.006).em,
    lineHeight = 20.dp
)
val Body_Long1 = pretendardTextStyle.copy(
    fontSize = 14.dp,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.006).em,
    lineHeight = 24.dp
)
val Caption = pretendardTextStyle.copy(
    fontSize = 12.dp,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.006).em,
    lineHeight = 18.dp
)

private val galmuri7FontFamily = FontFamily(
    Font(R.font.galmuri7)
)
private val galmuri7TextStyle = DpTextStyle(
    fontFamily = galmuri7FontFamily
)

val Display1_Point = galmuri7TextStyle.copy(
    fontSize = 34.dp,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.006).em,
    lineHeight = 52.dp
)
val Body1_Point = galmuri7TextStyle.copy(
    fontSize = 14.dp,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.006).em,
    lineHeight = 18.dp
)
val Body_Long2_Point = galmuri7TextStyle.copy(
    fontSize = 12.dp,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.006).em,
    lineHeight = 18.dp
)
