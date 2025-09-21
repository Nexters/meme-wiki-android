package com.example.mymeme.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mymeme.ui.component.MyMemeFooter
import com.example.mymeme.ui.model.Line
import com.example.mymeme.ui.navigation.MyMemeNavigationAction
import com.example.mymeme.ui.navigation.MyMemeNavigator
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.designsystem.theme.White
import com.mimu_bird.designsystem.typography.toTextStyle

@Composable
fun MyMemeScreen(
    id: String,
    viewModel: MyMemeViewModel = hiltViewModel(),
    navigator: MyMemeNavigator
) {
    val meme = viewModel.meme.collectAsStateWithLifecycle()
    val lines = viewModel.lines.collectAsStateWithLifecycle()
    val brush = viewModel.brush.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchMemeDetailInfo(id.toInt())
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Gray10),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 14.dp),
                    text = "취소",
                    style = Body2.toTextStyle(),
                    color = White
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 14.dp),
                    text = "완료",
                    style = Body2.toTextStyle(),
                    color = White
                )
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
                            detectDragGestures { change, amount ->
                                change.consume()
                                val line = Line(
                                    start = change.position - amount,
                                    end = change.position,
                                    brush = brush.value
                                )
                                viewModel.addLine(line)
                            }
                        }
                ) {
                    lines.value.forEach { line ->
                        drawLine(
                            color = line.brush.drawingColor,
                            start = line.start,
                            end = line.end,
                            strokeWidth = line.brush.drawingWidth.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
                MyMemeFooter(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 70.dp),
                    brush = brush.value,
                    onChangeColor = { viewModel.changeBrushColor(it) },
                    onChangeAlpha = { viewModel.changeBrushAlpha(it) },
                    onChangeWidth = { viewModel.changeBrushStroke(it) }
                )
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
        navigator = mockNavigator
    )
}