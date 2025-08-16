package com.example.mymeme.ui.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mymeme.ui.model.DrawingPath
import com.example.mymeme.ui.model.DrawingTool
import com.example.mymeme.ui.model.TextElement

@Composable
fun DrawingCanvas(
    imageUrl: String,
    drawingPaths: List<DrawingPath>,
    textElements: List<TextElement>,
    currentTool: DrawingTool,
    isTextMode: Boolean,
    onPathAdded: (DrawingPath) -> Unit,
    onTextAdded: (TextElement) -> Unit,
    onTextUpdated: (TextElement) -> Unit,
    onTextDeleted: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPath by remember { mutableStateOf<Path?>(null) }
    var currentPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var drawingSessionTool by remember { mutableStateOf(currentTool) }

    // 텍스트 편집 상태
    var currentText by remember { mutableStateOf("") }
    var currentTextColor by remember { mutableStateOf(currentTool.color) }
    var currentTextOpacity by remember { mutableStateOf(1.0f) }
    var isTextEditDialogVisible by remember { mutableStateOf(false) }
    var selectedTextId by remember { mutableStateOf<String?>(null) }
    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }

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
            .onSizeChanged { canvasSize = it }
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
                            if (isTextMode) {
                                // 텍스트 모드일 때는 텍스트 추가
                                val newText = TextElement(
                                    text = currentText.ifEmpty { "텍스트" },
                                    position = offset,
                                    color = currentTextColor,
                                    opacity = currentTextOpacity
                                )
                                onTextAdded(newText)
                                selectedTextId = newText.id
                            } else {
                                // 그리기 모드일 때는 선 그리기
                                drawingSessionTool = currentTool
                                currentPath = Path().apply {
                                    moveTo(offset.x, offset.y)
                                }
                                currentPoints = listOf(offset)
                                Log.d(
                                    "DrawingCanvas",
                                    "Started drawing with tool: ${drawingSessionTool}"
                                )
                            }
                        },
                        onDrag = { _, dragAmount ->
                            if (isTextMode) {
                                // 텍스트 모드일 때는 드래그 동작 없음 (텍스트 위치는 개별적으로 조정)
                            } else {
                                // 그리기 모드일 때는 선 그리기
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
                            }
                        },
                        onDragEnd = {
                            if (!isTextMode) {
                                // 그리기 모드일 때만 선 그리기 완료
                                currentPath?.let { path ->
                                    if (currentPoints.size > 1) {
                                        // 그리기 세션 시작 시점의 도구 설정으로 DrawingPath 생성
                                        val newPath = DrawingPath(
                                            points = currentPoints,
                                            strokeWidth = drawingSessionTool.strokeWidth.realWidth.toFloat(),
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
                        width = drawingSessionTool.strokeWidth.realWidth.toFloat(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }

        // 텍스트 요소들 렌더링 (isTextMode와 관계없이 항상 표시)
        // Canvas에 추가된 모든 텍스트는 모드가 변경되어도 계속 보임
        textElements.forEach { textElement ->
            Box(
                modifier = Modifier
                    .offset(
                        x = (textElement.position.x - 50).dp,
                        y = (textElement.position.y - 20).dp
                    )
                    .pointerInput(textElement.id) {
                        detectDragGestures(
                            onDrag = { _, dragAmount ->
                                val newPosition = Offset(
                                    textElement.position.x + dragAmount.x,
                                    textElement.position.y + dragAmount.y
                                )
                                onTextUpdated(textElement.copy(position = newPosition))
                            }
                        )
                    }
            ) {
                BasicTextField(
                    value = textElement.text,
                    onValueChange = { newText ->
                        onTextUpdated(textElement.copy(text = newText))
                    },
                    textStyle = TextStyle(
                        fontSize = textElement.fontSize.sp,
                        fontWeight = FontWeight.Medium,
                        color = textElement.color.color.copy(alpha = textElement.opacity)
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            selectedTextId = textElement.id
                            currentText = textElement.text
                            currentTextColor = textElement.color
                            currentTextOpacity = textElement.opacity
                            isTextEditDialogVisible = true
                        }
                )
            }
        }

        // DraggableTextInput을 언제든지 표시
        var additionalTextInputs by remember { mutableStateOf(0) }

        // 텍스트 모드가 활성화되면 자동으로 첫 번째 UI 생성 (기존 UI는 유지)
        LaunchedEffect(isTextMode) {
            if (isTextMode && additionalTextInputs == 0) {
                Log.d("DrawingCanvas", "텍스트 모드 활성화, 첫 번째 UI 생성")
                additionalTextInputs = 1
            }
            // 텍스트 모드가 false가 되어도 기존 UI들은 유지 (additionalTextInputs = 0으로 설정하지 않음)
        }

        // additionalTextInputs 상태 변화 로깅
        LaunchedEffect(additionalTextInputs) {
            Log.d("DrawingCanvas", "additionalTextInputs 상태 변화: $additionalTextInputs")
        }

        // 실제 화면 크기 가져오기
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        val screenHeight = configuration.screenHeightDp

        // 모든 DraggableTextInput들을 repeat으로 생성
        repeat(additionalTextInputs) { index ->
            var additionalCurrentText by remember { mutableStateOf("") }
            var additionalTextColor by remember { mutableStateOf(currentTextColor) }
            var additionalTextOpacity by remember { mutableStateOf(currentTextOpacity) }
            var isTextEditDialogVisible by remember { mutableStateOf(false) }  // 각 UI마다 개별적인 편집 다이얼로그 상태
            var currentPosition by remember { mutableStateOf(Offset.Zero) }  // 각 UI의 현재 위치

            // positionIndex는 0부터 시작하되, 각 UI마다 다른 위치에 배치
            val actualPositionIndex = index

            Log.d(
                "DrawingCanvas",
                "DraggableTextInput 렌더링: index=$index, actualPositionIndex=$actualPositionIndex, total=$additionalTextInputs"
            )

            DraggableTextInput(
                isTextMode = isTextMode,
                currentText = additionalCurrentText,
                onTextChange = { additionalCurrentText = it },
                currentTextColor = additionalTextColor,
                currentTextOpacity = additionalTextOpacity,
                onTextAdded = { text ->
                    Log.d("DrawingCanvas", "DraggableTextInput[$index]에서 텍스트 추가 호출됨: '$text'")
                    val centerOffset = Offset(
                        (canvasSize.width / 2).toFloat(),
                        (canvasSize.height / 2).toFloat()
                    )
                    val newText = TextElement(
                        text = text,
                        position = centerOffset,
                        color = additionalTextColor,
                        opacity = additionalTextOpacity
                    )
                    Log.d(
                        "DrawingCanvas",
                        "UI[$index]에서 새로운 TextElement 생성: id=${newText.id}, text='${newText.text}', color=${newText.color}, opacity=${newText.opacity}"
                    )
                    onTextAdded(newText)
                    selectedTextId = newText.id
                    Log.d(
                        "DrawingCanvas",
                        "UI[$index]의 TextElement이 Canvas에 추가됨, selectedTextId: $selectedTextId"
                    )
                },
                onEditClick = { position ->
                    Log.d("DrawingCanvas", "DraggableTextInput[$index]에서 편집하기 클릭됨, 위치: $position")
                    isTextEditDialogVisible = !isTextEditDialogVisible  // 토글 방식으로 변경
                },
                onDelete = {
                    Log.d("DrawingCanvas", "DraggableTextInput[$index]에서 삭제 요청됨")
                    // 해당 DraggableTextInput과 TextEditDialog 제거
                    additionalTextInputs--
                    Log.d(
                        "DrawingCanvas",
                        "UI[$index] 삭제됨, additionalTextInputs: $additionalTextInputs"
                    )
                },
                onPositionChanged = { position ->
                    currentPosition = position  // 현재 위치 업데이트
                },
                parentWidth = with(LocalDensity.current) { canvasSize.width.toDp() },
                parentHeight = with(LocalDensity.current) { canvasSize.height.toDp() },
                onAddNewInput = {
                    Log.d("DrawingCanvas", "DraggableTextInput[$index]에서 새로운 UI 생성 요청됨")
                    val previousCount = additionalTextInputs
                    additionalTextInputs++
                    Log.d(
                        "DrawingCanvas",
                        "UI[$index]에서 additionalTextInputs 증가: $previousCount -> $additionalTextInputs"
                    )
                },
                initialOffset = Offset(
                    (screenWidth / 2).toFloat(),
                    (screenHeight / 2).toFloat()
                ), // 화면 정가운데
                positionIndex = actualPositionIndex // 각 UI마다 다른 positionIndex
            )

            // 각 DraggableTextInput에 매칭되는 TextEditDialog 생성
            if (isTextEditDialogVisible && isTextMode) {
                TextEditDialog(
                    currentColor = additionalTextColor,
                    onColorChange = { additionalTextColor = it },
                    currentOpacity = additionalTextOpacity,
                    onOpacityChange = { additionalTextOpacity = it },
                    onDelete = {
                        Log.d("DrawingCanvas", "TextEditDialog[$index]에서 삭제 요청됨")
                        // 해당 DraggableTextInput과 TextEditDialog 제거
                        additionalTextInputs--
                        isTextEditDialogVisible = false
                        Log.d(
                            "DrawingCanvas",
                            "UI[$index] 삭제됨, additionalTextInputs: $additionalTextInputs"
                        )
                    },
                    onClose = {
                        isTextEditDialogVisible = false
                    },
                    modifier = Modifier.offset(
                        x = (currentPosition.x - 40).dp,
                        y = (currentPosition.y + 60).dp
                    )
                )
            }
        }
    }
}