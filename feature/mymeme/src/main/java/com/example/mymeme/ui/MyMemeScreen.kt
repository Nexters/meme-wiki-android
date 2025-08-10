package com.example.mymeme.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymeme.ui.component.DrawingCanvas
import com.example.mymeme.ui.component.DrawingToolBar
import com.example.mymeme.ui.model.DrawingColor
import com.example.mymeme.ui.model.DrawingPath
import com.example.mymeme.ui.model.DrawingTool
import com.mimu_bird.designsystem.theme.Gray8
import com.mimu_bird.ui.model.TEST_BRIEF_MEME_UI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMemeScreen(
    imgUrl: String
) {
    var drawingPaths by remember { mutableStateOf<List<DrawingPath>>(emptyList()) }
    var currentTool by remember {
        mutableStateOf(
            DrawingTool(
                strokeWidth = 4f,
                opacity = 1.0f,
                color = DrawingColor.RED
            )
        )
    }
    var isDrawingToolBarVisible by remember { mutableStateOf(false) }

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
                currentTool = currentTool,
                onPathAdded = { path ->
                    drawingPaths = drawingPaths + path
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
                    modifier = Modifier
                        .padding(bottom = 30.dp),
                    isExpanded = isDrawingToolBarVisible
                )

                // 플로팅 액션 버튼
                FloatingActionButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(bottom = 70.dp)
                        .fillMaxWidth(0.7f),
                    containerColor = Gray8,
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "그리기 도구",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                isDrawingToolBarVisible = !isDrawingToolBarVisible
                            }
                        )
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "텍스트",
                            tint = Color.White
                        )
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