package com.example.mymeme.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Gray7
import com.mimu_bird.designsystem.theme.Gray11

@Composable
fun BottomToolBar(
    isDrawingToolBarVisible: Boolean,
    isTextMode: Boolean,
    onDrawingToolBarVisibilityChanged: (Boolean) -> Unit,
    onTextModeChanged: (Boolean) -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .padding(bottom = 70.dp)
            .width(232.dp)
            .height(50.dp)
            .background(color = Gray11, shape = RoundedCornerShape(24.dp))
            .border(width = 1.dp, shape = RoundedCornerShape(24.dp), color = Gray11),
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
                        val newVisibility = !isDrawingToolBarVisible
                        onDrawingToolBarVisibilityChanged(newVisibility)
                        // 그리기 도구 바가 켜지면 텍스트 모드 끄기
                        if (newVisibility) {
                            onTextModeChanged(false)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_pen),
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
                        val newTextMode = !isTextMode
                        onTextModeChanged(newTextMode)
                        // 텍스트 모드가 켜지면 그리기 도구 바 끄기
                        if (newTextMode) {
                            onDrawingToolBarVisibilityChanged(false)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_text),
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
                        onUndo()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_previous),
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
                        onRedo()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "다시 실행",
                    tint = Color.White
                )
            }
        }
    }
} 