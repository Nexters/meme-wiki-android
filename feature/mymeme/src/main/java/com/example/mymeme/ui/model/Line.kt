package com.example.mymeme.ui.model

import androidx.compose.ui.geometry.Offset

data class Line (
    val start: Offset,
    val end: Offset,
    val brush: Brush = Brush.DEFAULT
)