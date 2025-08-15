package com.example.mymeme.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.mymeme.ui.component.DrawingCanvas
import com.example.mymeme.ui.component.DrawingToolBar
import com.example.mymeme.ui.model.DrawingColor
import com.example.mymeme.ui.model.DrawingPath
import com.example.mymeme.ui.model.DrawingTool
import com.example.mymeme.ui.model.TextElement
import com.example.mymeme.ui.model.Width
import com.mimu_bird.designsystem.theme.Gray7
import com.mimu_bird.designsystem.theme.Gray8
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
                            contentDescription = "뒤로 가기"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            // TODO: 실제 저장 로직 구현
                            {}
                        }
                    ) {
                        Text("완료")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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
                Row(
                    modifier = Modifier
                        .padding(bottom = 70.dp)
                        .width(232.dp)
                        .height(50.dp)
                        .background(color = Gray8, shape = RoundedCornerShape(24.dp))
                        .border(width = 1.dp, shape = RoundedCornerShape(24.dp), color = Gray8),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(Modifier.width(72.dp), horizontalArrangement = Arrangement.Center) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(
                                    color = if (isDrawingToolBarVisible) Gray7 else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    isDrawingToolBarVisible = !isDrawingToolBarVisible
                                    // 그리기 도구 바가 켜지면 텍스트 모드 끄기
                                    if (isDrawingToolBarVisible) {
                                        isTextMode = false
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_pen),
                                contentDescription = "그리기 도구",
                                tint = Color.White
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(
                                    color = if (isTextMode) Gray7 else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    // 오직 플로팅 버튼의 텍스트 아이콘을 클릭했을 때만 텍스트 모드 변경
                                    isTextMode = !isTextMode
                                    // 텍스트 모드가 켜지면 그리기 도구 바 끄기
                                    if (isTextMode) {
                                        isDrawingToolBarVisible = false
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_text),
                                contentDescription = "텍스트",
                                tint = Color.White
                            )
                        }
                    }


                    // 수직 구분선
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(18.dp)
                            .background(color = Gray7)
                    )
                    Row(Modifier.width(72.dp), horizontalArrangement = Arrangement.Center) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    // TODO: 실행 취소 구현
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_previous),
                                contentDescription = "실행 취소",
                                tint = Color.White
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    // TODO: 다시 실행 구현
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_next),
                                contentDescription = "다시 실행",
                                tint = Color.White
                            )
                        }
                    }
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