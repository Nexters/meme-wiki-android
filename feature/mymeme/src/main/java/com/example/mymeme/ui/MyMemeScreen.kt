package com.example.mymeme.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymeme.ui.component.BottomToolBar
import com.example.mymeme.ui.component.DrawingCanvas
import com.example.mymeme.ui.component.DrawingToolBar
import com.example.mymeme.ui.component.SaveButton
import com.example.mymeme.ui.model.DrawingColor
import com.example.mymeme.ui.model.DrawingPath
import com.example.mymeme.ui.model.DrawingTool
import com.example.mymeme.ui.model.TextElement
import com.example.mymeme.ui.model.Width
import com.mimu_bird.designsystem.theme.Black
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.TEST_BRIEF_MEME_UI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMemeScreen(
    imgUrl: String
) {
    var drawingPaths by remember { mutableStateOf<List<DrawingPath>>(emptyList()) }
    var textElements by remember { mutableStateOf<List<TextElement>>(emptyList()) }
    var currentTool by remember {
        mutableStateOf(
            DrawingTool(
                strokeWidth = Width.LEVEL3,
                opacity = 1.0f,
                color = DrawingColor.RED
            )
        )
    }
    var isDrawingToolBarVisible by remember { mutableStateOf(false) }
    var isTextMode by remember { mutableStateOf(false) }
    var isBottomToolBarVisible by remember { mutableStateOf(true) }
    var isEditMode by remember { mutableStateOf(true) }  // 편집 모드 상태 (true: 편집, false: 저장)
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(currentTool) {
        Log.d("MyMemeScreen", "currentTool:${currentTool}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // TODO: back navigation
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로 가기",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (isEditMode) {
                        // 편집 모드일 때: "완료" 텍스트 표시
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                isEditMode = !isEditMode
                                if (isEditMode) {
                                    isBottomToolBarVisible = true  // 편집 모드로 전환 시 플로팅 버튼 표시
                                } else {
                                    isBottomToolBarVisible = false  // 저장 모드로 전환 시 플로팅 버튼 숨김
                                }
                            }
                        ) {
                            Text(
                                text = "완료",
                                style = Body2.toTextStyle(),
                                color = Color.White,
                            )
                        }
                    } else {
                        // 저장 모드일 때: ic_toolbox 아이콘 표시
                        IconButton(
                            onClick = {
                                isEditMode = !isEditMode
                                if (isEditMode) {
                                    isBottomToolBarVisible = true  // 편집 모드로 전환 시 플로팅 버튼 표시
                                } else {
                                    isBottomToolBarVisible = false  // 저장 모드로 전환 시 플로팅 버튼 숨김
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_toolbox),
                                contentDescription = "도구 상자",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier.background(color = Black)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black),
            contentAlignment = Alignment.BottomCenter
        ) {
            // 그리기 캔버스
            DrawingCanvas(
                imageUrl = imgUrl,
                drawingPaths = drawingPaths,
                textElements = textElements,
                currentTool = currentTool,
                isTextMode = isTextMode,
                onPathAdded = { path ->
                    drawingPaths = drawingPaths + path
                },
                onTextAdded = { textElement ->
                    textElements = textElements + textElement
                    // 텍스트 모드는 사용자가 직접 변경해야 함
                },
                onTextUpdated = { updatedText ->
                    textElements = textElements.map {
                        if (it.id == updatedText.id) updatedText else it
                    }
                },
                onTextDeleted = { textId ->
                    textElements = textElements.filter { it.id != textId }
                },
                modifier = Modifier.fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // 그리기 도구 바 (우측 하단)
                DrawingToolBar(
                    currentTool = currentTool,
                    onToolChanged = { tool ->
                        currentTool = tool
                    },
                    onClose = {
                        isDrawingToolBarVisible = false
                    },
                    isExpanded = isDrawingToolBarVisible
                )

                // 하단 도구 모음 (원래 구조 복원)
                if (isEditMode && isBottomToolBarVisible) {
                    BottomToolBar(
                        isDrawingToolBarVisible = isDrawingToolBarVisible,
                        isTextMode = isTextMode,
                        onDrawingToolBarVisibilityChanged = { newVisibility ->
                            isDrawingToolBarVisible = newVisibility
                            // 그리기 도구 바가 켜지면 텍스트 모드 끄기
                            if (newVisibility) {
                                isTextMode = false
                            }
                        },
                        onTextModeChanged = { newTextMode ->
                            // 오직 플로팅 버튼의 텍스트 아이콘을 클릭했을 때만 텍스트 모드 변경
                            isTextMode = newTextMode
                            // 텍스트 모드가 켜지면 그리기 도구 바 끄기
                            if (newTextMode) {
                                isDrawingToolBarVisible = false
                            }
                        },
                        onUndo = {
                            // TODO: 실행 취소 구현
                        },
                        onRedo = {
                            // TODO: 다시 실행 구현
                        }
                    )
                } else if (!isEditMode) {
                    SaveButton(
                        onSave = {
                            // TODO: 실제 저장 로직 구현
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyMemeScreen() {
    MyMemeScreen(TEST_BRIEF_MEME_UI.imageUrl)
}