package com.example.mymeme.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymeme.ui.model.DrawingColor
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Black
import com.mimu_bird.designsystem.theme.Gray2
import com.mimu_bird.designsystem.theme.White

@Composable
fun TextEditDialog(
    currentColor: DrawingColor,
    onColorChange: (DrawingColor) -> Unit,
    currentOpacity: Float,
    onOpacityChange: (Float) -> Unit,
    onDelete: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(312.dp)
            .height(130.dp),
        contentAlignment = Alignment.Center
    ) {
        // 배경으로 polygon_bubble 이미지
        Image(
            painter = painterResource(R.drawable.polygon_bubble),
            contentDescription = "말풍선 모양 배경",
        )

        // 기존 컴포넌트들을 위에 겹치기
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            Spacer(Modifier.height(20.dp))
            // 투명도 슬라이더
            OpacitySlider(
                value = currentOpacity,
                onValueChange = onOpacityChange,
                selectedColor = currentColor.color,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 색상 선택
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DrawingColor.values().forEach { color ->
                    val isSelected = currentColor == color
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(color.color)
                            .clickable {
                                onColorChange(color)
                            }
                            .then(
                                if (isSelected) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = if (color.color == White) Gray2 else color.color,
                                        shape = CircleShape
                                    )
                                } else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = "선택된 색상",
                                tint = Black,
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
fun TextEditDialogPreview() {
    TextEditDialog(
        currentColor = com.example.mymeme.ui.model.DrawingColor.BLUE,
        onColorChange = {},
        currentOpacity = 0.8f,
        onOpacityChange = {},
        onDelete = {},
        onClose = {},
        modifier = Modifier.padding(16.dp)
    )
}

