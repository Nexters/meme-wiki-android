package com.example.mymeme.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Caption
import com.mimu_bird.designsystem.theme.Gray2
import com.mimu_bird.designsystem.theme.Gray7
import com.mimu_bird.designsystem.typography.toTextStyle

@Composable
fun OpacitySlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    selectedColor: Color,
    modifier: Modifier = Modifier
) {
    val sliderBackground = painterResource(id = R.drawable.checkboard)
    val density = LocalDensity.current
    val sliderWidth = 272.dp
    val sliderHeight = 22.dp
    val thumbSize = 24.dp
    var offsetX by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .width(sliderWidth)
            .height(sliderHeight)
    ) {
        // 배경과 그라데이션이 있는 트랙
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        ) {
            // 체크보드 배경
            val imageBitmap = sliderBackground.toImageBitmap(
                density = density,
                layoutDirection = layoutDirection,
                targetSizeDp = 294.dp,
                targetHeightDp = 22.dp
            )

            // DP를 픽셀로 변환
            val widthPx = with(density) { 294.dp.roundToPx() }
            val heightPx = with(density) { 22.dp.roundToPx() }

            drawImage(
                image = imageBitmap,
                dstSize = IntSize(widthPx, heightPx)  // 픽셀 단위
            )

            // 선택된 컬러의 그라데이션
            val brush = Brush.horizontalGradient(
                colors = listOf(
                    selectedColor.copy(alpha = 0.1f),
                    selectedColor.copy(alpha = 0.5f),
                    selectedColor.copy(alpha = 1.0f)
                ),
                startX = 0f,
                endX = size.width
            )

            drawRect(
                brush = brush,
                topLeft = Offset.Zero,
                size = size
            )
        }

        // 드래그 가능한 thumb
        Box(
            modifier = Modifier
                .size(thumbSize)
                .offset(
                    x = with(LocalDensity.current) {
                        val basePosition = ((value - 0.1f) / 0.9f * (sliderWidth - thumbSize))
                        basePosition + offsetX.toDp()
                    },
                    y = (sliderHeight - thumbSize) / 2
                )
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = Gray2,
                    shape = CircleShape
                )
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        // delta를 dp로 변환
                        val deltaDp = with(density) { delta.toDp() }
                        val offsetXDp = with(density) { offsetX.toDp() }
                        val availableWidth = sliderWidth - thumbSize
                        val currentPosition = ((value - 0.1f) / 0.9f * availableWidth)
                        val newPosition =
                            (currentPosition + offsetXDp + deltaDp).coerceIn(0.dp, availableWidth)

                        // offsetX 업데이트 (픽셀 단위로 유지하되 계산은 dp로)
                        val newOffsetDp = newPosition - currentPosition
                        offsetX = with(density) { newOffsetDp.toPx() }

                        // 새로운 value 계산 및 전달
                        val newValue =
                            (newPosition / availableWidth * 0.9f + 0.1f).coerceIn(0.1f, 1.0f)
                        onValueChange(newValue)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${(value * 100).toInt()}",
                color = Gray7,
                style = Caption.toTextStyle()
            )
        }
    }
}