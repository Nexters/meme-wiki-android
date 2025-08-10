package com.example.mymeme.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymeme.ui.model.ColorCrayon
import com.example.mymeme.ui.model.DrawingColor
import com.example.mymeme.ui.model.DrawingTool
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Gray5
import com.mimu_bird.designsystem.theme.Gray7
import com.mimu_bird.designsystem.theme.Gray9

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingToolBar(
    currentTool: DrawingTool,
    onToolChanged: (DrawingTool) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
) {
    val WIDTHS = listOf<Int>(3, 6, 9, 12, 15)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {

        // 도구 선택 패널
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Card(
                modifier = Modifier.width(312.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Gray9,
                    contentColor = Gray9
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 14.dp, bottom = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 헤더
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onClose) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "닫기",
                                tint = Gray5
                            )
                        }
                    }
                    Column(
                        modifier
                            .fillMaxWidth(1f)
                            .padding(top = 16.dp, bottom = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val currentColor = currentTool.color
                        var painterResourceId = when (currentColor) {
                            DrawingColor.RED -> ColorCrayon.RED.painterResourceId
                            DrawingColor.ORANGE -> ColorCrayon.ORANGE.painterResourceId
                            DrawingColor.YELLOW -> ColorCrayon.YELLOW.painterResourceId
                            DrawingColor.GREEN -> ColorCrayon.GREEN.painterResourceId
                            DrawingColor.BLUE -> ColorCrayon.BLUE.painterResourceId
                            DrawingColor.NAVY -> ColorCrayon.NAVY.painterResourceId
                            DrawingColor.WHITE -> ColorCrayon.WHITE.painterResourceId
                            DrawingColor.BLACK -> ColorCrayon.BLACK.painterResourceId
                        }
                        Image(
                            modifier = Modifier.height(129.dp),
                            painter = painterResource(painterResourceId),
                            contentDescription = "색상 선택 결과 보여주는 크레용 이미지",
                            contentScale = ContentScale.FillHeight
                        )
                    }

                    // 펜 굵기 선택
                    Column {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(25.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = Gray7,
                                contentColor = Gray7
                            )
                        ) {
                            Row(
                                Modifier
                                    .background(color = Gray7)
                                    .fillMaxWidth(1f)
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                WIDTHS.forEach { width ->
                                    val isSelected = currentTool.strokeWidth.toInt() == width
                                    Box(
                                        modifier = Modifier
                                            .size(if (isSelected) 26.dp else width.dp)
                                            .clip(CircleShape)
                                            .background(color = Color.White)
                                            .clickable {
                                                onToolChanged(currentTool.copy(strokeWidth = width.toFloat()))
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = if (isSelected) "$width" else "",
                                            color = Color.Black,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 투명도 조절
                    OpacitySlider(
                        value = currentTool.opacity,
                        onValueChange = { opacity ->
                            onToolChanged(currentTool.copy(opacity = opacity))
                        },
                        selectedColor = currentTool.color.color,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // 색상 선택
                    Row(
                        Modifier.fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(DrawingColor.values().size) { index ->
                            val color = DrawingColor.values()[index]
                            val isSelected = currentTool.color == color
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(color.color)
                                    .clickable {
                                        onToolChanged(currentTool.copy(color = color))
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_check),
                                        contentDescription = "selected color"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Painter.toImageBitmap(
    size: Size,
    density: Density,
    layoutDirection: LayoutDirection,
): ImageBitmap {
    val bitmap = ImageBitmap(272, size.height.toInt())
    val canvas = Canvas(bitmap)
    CanvasDrawScope().draw(density, layoutDirection, canvas, size) {
        draw(size)
    }
    return bitmap
}
