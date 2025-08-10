package com.example.mymeme.ui.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.mymeme.ui.model.DrawingPath
import com.example.mymeme.ui.model.DrawingTool

@Composable
fun DrawingCanvas(
    imageUrl: String,
    drawingPaths: List<DrawingPath>,
    currentTool: DrawingTool,
    onPathAdded: (DrawingPath) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPath by remember { mutableStateOf<Path?>(null) }
    var currentPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var drawingSessionTool by remember { mutableStateOf(currentTool) }

    // currentTool 변경을 로그로만 확인 (currentPath 업데이트 제거)
    LaunchedEffect(currentTool) {
        Log.d("DrawingCanvas", "currentTool changed to: ${currentTool}")
        // 새로운 그리기 세션이 시작되지 않은 상태에서만 drawingSessionTool 업데이트
        if (currentPath == null) {
            drawingSessionTool = currentTool
            Log.d("DrawingCanvas", "Updated drawingSessionTool to: ${drawingSessionTool}")
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        // 배경 이미지
        AsyncImage(
            model = imageUrl,
            contentDescription = "편집할 밈 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 그리기 캔버스
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(currentTool) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            // 새로운 그리기 시작 시 currentTool 설정으로 초기화
                            drawingSessionTool = currentTool
                            currentPath = Path().apply {
                                moveTo(offset.x, offset.y)
                            }
                            currentPoints = listOf(offset)
                            Log.d(
                                "DrawingCanvas",
                                "Started drawing with tool: ${drawingSessionTool}"
                            )
                        },
                        onDrag = { _, dragAmount ->
                            currentPath?.let { path ->
                                path.lineTo(
                                    currentPoints.last().x + dragAmount.x,
                                    currentPoints.last().y + dragAmount.y
                                )
                                currentPoints = currentPoints + Offset(
                                    currentPoints.last().x + dragAmount.x,
                                    currentPoints.last().y + dragAmount.y
                                )
                            }
                        },
                        onDragEnd = {
                            currentPath?.let { path ->
                                if (currentPoints.size > 1) {
                                    // 그리기 세션 시작 시점의 도구 설정으로 DrawingPath 생성
                                    val newPath = DrawingPath(
                                        points = currentPoints,
                                        strokeWidth = drawingSessionTool.strokeWidth,
                                        opacity = drawingSessionTool.opacity,
                                        color = drawingSessionTool.color
                                    )
                                    onPathAdded(newPath)
                                    Log.d(
                                        "DrawingCanvas",
                                        "Added path with tool: ${drawingSessionTool}"
                                    )
                                }
                            }
                            currentPath = null
                            currentPoints = emptyList()
                        }
                    )
                }
        ) {
            // 기존 그리기 경로들 그리기
            drawingPaths.forEach { path ->
                val drawPath = Path().apply {
                    if (path.points.isNotEmpty()) {
                        moveTo(path.points.first().x, path.points.first().y)
                        path.points.drop(1).forEach { point ->
                            lineTo(point.x, point.y)
                        }
                    }
                }

                drawPath(
                    path = drawPath,
                    color = path.color.color.copy(alpha = path.opacity),
                    style = Stroke(
                        width = path.strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // 현재 그리기 중인 경로 그리기 (그리기 세션 도구 설정으로 실시간 그리기)
            currentPath?.let { path ->
                drawPath(
                    path = path,
                    color = drawingSessionTool.color.color.copy(alpha = drawingSessionTool.opacity),
                    style = Stroke(
                        width = drawingSessionTool.strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}