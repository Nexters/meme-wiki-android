package com.example.mymeme.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mymeme.ui.component.MyMemeFooter
import com.example.mymeme.ui.model.Line
import com.example.mymeme.ui.model.pointsToPath
import com.example.mymeme.ui.navigation.MyMemeNavigationAction
import com.example.mymeme.ui.navigation.MyMemeNavigator
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.designsystem.theme.White
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.mymeme.R

@Composable
fun MyMemeScreen(
    id: String,
    viewModel: MyMemeViewModel = hiltViewModel(),
    navigator: MyMemeNavigator,
    onClickBackPressed: () -> Unit
) {
    val meme = viewModel.meme.collectAsStateWithLifecycle()
    val lines = viewModel.lines.collectAsStateWithLifecycle()
    val brush = viewModel.brush.collectAsStateWithLifecycle()
    val histories = viewModel.histories.collectAsStateWithLifecycle()
    val isEditMode = viewModel.isEditMode.collectAsStateWithLifecycle()

    val currentPath = remember { mutableStateListOf<Offset>() }

    LaunchedEffect(Unit) {
        viewModel.fetchMemeDetailInfo(id.toInt())
    }

    BackHandler {
        if (isEditMode.value) onClickBackPressed()
        else viewModel.setIsEditMode(true)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray10)
            .windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Gray10),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isEditMode.value) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 14.dp)
                            .clickable { onClickBackPressed() },
                        text = "취소",
                        style = Body2.toTextStyle(),
                        color = White
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 14.dp)
                            .clickable { viewModel.setIsEditMode(false) },
                        text = "완료",
                        style = Body2.toTextStyle(),
                        color = White
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .clickable { viewModel.setIsEditMode(true) }
                            .padding(vertical = 10.dp, horizontal = 14.dp)
                            .size(24.dp),
                        painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_back),
                        contentDescription = "수정 화면으로 이동",
                        tint = White
                    )
                    Icon(
                        modifier = Modifier
                            .clickable { navigator.navigate(MyMemeNavigationAction.NavigateMain) }
                            .padding(vertical = 10.dp, horizontal = 14.dp)
                            .size(24.dp),
                        painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_home_24),
                        contentDescription = "홈화면 으로 이동",
                        tint = White
                    )
                }
            }
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            meme.value?.let {
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    model = it.imageUrl,
                    contentDescription = it.title,
                    contentScale = ContentScale.Fit
                )

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(true) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    if (isEditMode.value) {
                                        currentPath.clear()
                                        currentPath.add(offset)
                                    }
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    if (isEditMode.value) currentPath.add(change.position)
                                },
                                onDragEnd = {
                                    if (currentPath.isNotEmpty() && isEditMode.value) {
                                        viewModel.addLine(
                                            Line(path = currentPath.toList(), brush = brush.value)
                                        )
                                        currentPath.clear()
                                    }
                                }
                            )
                        }
                ) {
                    lines.value.forEach { line ->
                        drawPath(
                            path = pointsToPath(line.path),
                            color = line.brush.drawingColor,
                            style = Stroke(
                                width = line.brush.drawingWidth.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }

                    // 드래그 중 라인
                    if (currentPath.size > 1) {
                        drawPath(
                            path = pointsToPath(currentPath),
                            color = brush.value.drawingColor,
                            style = Stroke(
                                width = brush.value.drawingWidth.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }
                if (isEditMode.value) {
                    MyMemeFooter(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 70.dp),
                        isEditMode = isEditMode.value,
                        brush = brush.value,
                        isAblePrev = lines.value.isNotEmpty(),
                        isAbleRollback = histories.value.isNotEmpty(),
                        onChangeColor = { viewModel.changeBrushColor(it) },
                        onChangeAlpha = { viewModel.changeBrushAlpha(it) },
                        onChangeWidth = { viewModel.changeBrushStroke(it) },
                        onClickPrev = { viewModel.popLine() },
                        onClickRollback = { viewModel.rollbackLine() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyMemeScreen() {
    val mockNavigator = object : MyMemeNavigator {
        override fun navigate(action: MyMemeNavigationAction) {
            // Preview에서는 아무것도 하지 않음
        }
    }

    MyMemeScreen(
        id = "1",
        navigator = mockNavigator,
        onClickBackPressed = {}
    )
}