package com.example.mymeme.ui.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Line (
    val start: Offset,
    val end: Offset,
    val color: Color = Color.White,
    val strokeWidth: Dp = 1.dp
)