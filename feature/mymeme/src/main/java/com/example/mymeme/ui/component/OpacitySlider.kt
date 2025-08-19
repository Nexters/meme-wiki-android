package com.example.mymeme.ui.component

import androidx.compose.foundation.Canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
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
    val thumbSize = 26.dp
        var offsetX by remember { 
        mutableStateOf(
            // 초기 value에 맞는 offsetX 설정
            with(density) { 
                ((value - 0.1f) / 0.9f * (sliderWidth - thumbSize)).toPx() 
            }
        )
    }
    
    // thumb의 x 위치를 메모이제이션
    val thumbXPosition = remember(offsetX) {
        with(density) {
            offsetX.toDp().coerceIn(0.dp, sliderWidth - thumbSize)
        }
    }
    


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
                targetSizeDp = sliderWidth,
                targetHeightDp = sliderHeight
            )

            // DP를 픽셀로 변환
            val widthPx = with(density) { sliderWidth.roundToPx() }
            val heightPx = with(density) { sliderHeight.roundToPx() }

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

        // 확장된 터치 영역에 pointerInput 적용 (투명하게)
        Box(
            modifier = Modifier
                .width(sliderWidth)
                .height(sliderHeight + 80.dp)  // 세로 방향으로 80dp 확장
                .offset(y = (0).dp)  // 위쪽으로 40dp 이동하여 중앙 정렬
                .background(Color.Transparent)  // 투명 배경으로 UI에 영향 없음
                .pointerInput(Unit) {
                    // availableWidth를 한 번만 계산
                    val availableWidth = sliderWidth - thumbSize
                    
                    detectTapGestures { offset ->
                        // 터치한 위치를 기준으로 thumb 이동
                        val touchPosition = with(density) { offset.x.toDp() }
                        
                        // 터치 위치를 availableWidth 범위로 제한 (thumb이 슬라이더 안에 머무르도록)
                        val newPosition = touchPosition.coerceIn(0.dp, availableWidth)
                        
                        // offsetX 업데이트
                        val newOffsetX = with(density) { newPosition.toPx() }
                        offsetX = newOffsetX
                        
                        // 새로운 value 계산 및 전달
                        val newValue = (newPosition / availableWidth * 0.9f + 0.1f).coerceIn(0.1f, 1.0f)
                        onValueChange(newValue)
                    }
                    
                    detectDragGestures(
                        onDragStart = { offset ->
                            // 드래그 시작 시 터치 위치로 thumb 이동
                            val touchPosition = with(density) { offset.x.toDp() }
                            
                            // thumb이 슬라이더 안에 머무르도록 제한
                            val newPosition = touchPosition.coerceIn(0.dp, availableWidth)
                            val newOffsetX = with(density) { newPosition.toPx() }
                            offsetX = newOffsetX
                        },
                        onDrag = { change, _ ->
                            // 드래그 중에는 offsetX만 업데이트 (value 업데이트 제거)
                            val touchPosition = with(density) { change.position.x.toDp() }
                            
                            // thumb이 슬라이더 안에 머무르도록 제한
                            val newPosition = touchPosition.coerceIn(0.dp, availableWidth)
                            val newOffsetX = with(density) { newPosition.toPx() }
                            offsetX = newOffsetX
                        },
                        onDragEnd = {
                            // 드래그 종료 시에만 최종 value 계산 및 전달
                            val finalPosition = with(density) { offsetX.toDp() }
                            
                            val finalValue = (finalPosition / availableWidth * 0.9f + 0.1f).coerceIn(0.1f, 1.0f)
                            onValueChange(finalValue)
                        }
                    )
                }
        )
        
        // 드래그 가능한 thumb을 메인 Box의 자식으로 이동
        Box(
            modifier = Modifier
                .size(thumbSize)
                .offset(
                    x = thumbXPosition,
                    y = (sliderHeight - thumbSize) / 2  // 세로 중앙 정렬
                )
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = Gray2,
                    shape = CircleShape
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