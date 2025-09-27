package com.example.mymeme.ui.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

data class Line (
    val path: List<Offset>,
    val brush: Brush = Brush.DEFAULT
)

fun pointsToPath(points: List<Offset>): Path {
    val path = Path()
    if (points.isEmpty()) return path

    path.moveTo(points.first().x, points.first().y)
    for (i in 1 until points.size) {
        val prev = points[i - 1]
        val current = points[i]
        path.quadraticBezierTo(
            prev.x, prev.y,                       // control point
            (prev.x + current.x) / 2, (prev.y + current.y) / 2 // end point
        )
    }
    return path
}