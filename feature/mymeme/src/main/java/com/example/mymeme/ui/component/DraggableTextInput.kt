package com.example.mymeme.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymeme.ui.model.DrawingColor
import com.mimu_bird.designsystem.theme.Blue60

@Composable
fun DraggableTextInput(
    currentText: String,
    onTextChange: (String) -> Unit,
    currentTextColor: DrawingColor,
    currentTextOpacity: Float,
    onTextAdded: (String) -> Unit,
    onEditClick: () -> Unit,
    onAddNewInput: () -> Unit = {},
    parentWidth: Dp,  // 부모 컴포넌트의 너비 (dp)
    parentHeight: Dp, // 부모 컴포넌트의 높이 (dp)
    initialOffset: Offset = Offset(0f, 0f), // 초기 위치 오프셋
    positionIndex: Int = 0, // 위치 인덱스 (외부에서 주입)
    modifier: Modifier = Modifier
) {
    var textInputPosition by remember { mutableStateOf(initialOffset) }
    var isButtonsEnabled by remember { mutableStateOf(true) }

    // positionIndex를 사용하여 위치 계산
    val positionOffset = when (positionIndex % 5) {
        0 -> Offset(0f, 0f)      // 정가운데
        1 -> Offset(0f, -40f)    // 위
        2 -> Offset(0f, 40f)     // 아래
        3 -> Offset(40f, 0f)     // 오른쪽
        4 -> Offset(-40f, 0f)    // 왼쪽
        else -> Offset(0f, 0f)   // 기본값
    }

    // 실제 위치 = 초기 위치 + positionIndex에 따른 오프셋
    val actualPosition = Offset(
        initialOffset.x + positionOffset.x,
        initialOffset.y + positionOffset.y
    )

    // 컴포넌트가 처음 렌더링될 때 actualPosition으로 설정
    LaunchedEffect(positionIndex) {
        textInputPosition = actualPosition
    }

    Box(
        modifier = modifier
            .offset(
                x = textInputPosition.x.dp,
                y = textInputPosition.y.dp
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        // 화면 경계 제한을 위한 새로운 위치 계산
                        val newX = textInputPosition.x + dragAmount.x
                        val newY = textInputPosition.y + dragAmount.y

                        // 컴포넌트 크기 (Column의 전체 크기)
                        val componentWidth = 215f  // 가장 넓은 요소의 width
                        val componentHeight = 200f // Column의 전체 height (대략적)

                        // 경계 제한된 위치 계산
                        val limitedX = newX.coerceIn(
                            -componentWidth + 50f,  // 왼쪽 경계 (완전히 사라지지 않도록 여유 공간)
                            parentWidth.value - 50f       // 오른쪽 경계 (완전히 사라지지 않도록 여유 공간)
                        )
                        val limitedY = newY.coerceIn(
                            -componentHeight + 50f, // 위쪽 경계 (완전히 사라지지 않도록 여유 공간)
                            parentHeight.value - 50f      // 아래쪽 경계 (완전히 사라지지 않도록 여유 공간)
                        )

                        textInputPosition = Offset(limitedX, limitedY)
                    }
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 텍스트 추가와 편집하기 버튼을 하나의 Row에 합침
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 텍스트 추가 버튼
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(
                            color = if (isButtonsEnabled) Color.White else Color.Red,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(enabled = isButtonsEnabled) {
                            // 텍스트가 입력되어 있을 때만 추가
                            if (currentText.isNotEmpty()) {
                                onTextAdded(currentText)
                                isButtonsEnabled = false
                                // 새로운 텍스트 입력 UI 생성
                                onAddNewInput()
                            }
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "텍스트 추가",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = if (isButtonsEnabled) Color.Black else Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                // 편집하기 버튼
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(
                            color = if (isButtonsEnabled) Color.White else Color.Red,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(enabled = isButtonsEnabled) {
                            onEditClick()
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "편집하기",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = if (isButtonsEnabled) Color.Black else Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            // 텍스트 입력 필드
            BasicTextField(
                value = currentText,
                onValueChange = onTextChange,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = currentTextColor.color.copy(alpha = currentTextOpacity),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .size(200.dp, 50.dp)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .background(
                        color = Color.Transparent,
                    )
                    .border(
                        width = 1.dp,
                        color = Blue60
                    )
                    .clickable {
                        // BasicTextField 클릭 시 버튼들 다시 활성화
                        isButtonsEnabled = true
                    }
            )
        }
    }
} 