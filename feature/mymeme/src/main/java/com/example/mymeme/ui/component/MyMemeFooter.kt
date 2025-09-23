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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymeme.ui.model.Brush
import com.example.mymeme.ui.model.BrushColor
import com.example.mymeme.ui.model.BrushWidth
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.theme.Caption
import com.mimu_bird.designsystem.theme.Gray2
import com.mimu_bird.designsystem.theme.Gray5
import com.mimu_bird.designsystem.theme.Gray7
import com.mimu_bird.designsystem.theme.Gray8
import com.mimu_bird.designsystem.theme.Gray9
import com.mimu_bird.designsystem.theme.White
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.mymeme.R

@Preview
@Composable
private fun MyMemeFooterPreview() {
    MyMemeFooter(
        isEditMode = false,
        brush = Brush.DEFAULT,
        isAblePrev = false,
        isAbleRollback = true,
        onChangeColor = {},
        onChangeAlpha = {},
        onChangeWidth = {},
        onClickPrev = {},
        onClickRollback = {},
        onClickSave = {}
    )
}

@Composable
internal fun MyMemeFooter(
    modifier: Modifier = Modifier,
    isEditMode: Boolean,
    brush: Brush,
    isAblePrev: Boolean,
    isAbleRollback: Boolean,
    onChangeColor: (BrushColor) -> Unit,
    onChangeAlpha: (Float) -> Unit,
    onChangeWidth: (BrushWidth) -> Unit,
    onClickPrev: () -> Unit,
    onClickRollback: () -> Unit,
    onClickSave: () -> Unit
) {
    var isShow by remember(isEditMode) { mutableStateOf(false) }

    Column(
        modifier = modifier
            .then(
                if (isEditMode) Modifier
                else Modifier.clickable { onClickSave() }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isShow) {
            MyMemePalette(
                modifier = Modifier
                    .padding(bottom = 20.dp, start = 32.dp, end = 32.dp)
                    .widthIn(max = 312.dp),
                brush = brush,
                onChangeColor = onChangeColor,
                onChangeAlpha = onChangeAlpha,
                onChangeWidth = onChangeWidth,
                onClickClose = { isShow = false }
            )
        }
        Row(
            modifier = Modifier
                .background(
                    color = Gray9.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(50.dp)
                )
                .border(
                    width = 1.dp,
                    color = Gray8,
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 24.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (isEditMode) 12.dp else 4.dp)
        ) {
            if (isEditMode) {
                Icon(
                    modifier = Modifier
                        .clickable { isShow = true }
                        .padding(8.dp),
                    painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_pen),
                    contentDescription = "펜",
                    tint = White
                )
                Icon(
                    modifier = Modifier
                        .clickable { }
                        .padding(8.dp),
                    painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_text),
                    contentDescription = "펜",
                    tint = White
                )
                Spacer(
                    modifier = Modifier
                        .size(width = 1.dp, height = 18.dp)
                        .background(Gray7),
                )
                Icon(
                    modifier = Modifier
                        .clickable { onClickPrev() }
                        .padding(8.dp),
                    painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_previous),
                    contentDescription = "펜",
                    tint = if (isAblePrev) White else Gray7
                )
                Icon(
                    modifier = Modifier
                        .clickable { onClickRollback() }
                        .padding(8.dp),
                    painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_next),
                    contentDescription = "펜",
                    tint = if (isAbleRollback) White else Gray7
                )
            } else {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_save),
                    contentDescription = "다운로드",
                    tint = White
                )
                Text(
                    text = "저장하기",
                    style = Body2.toTextStyle(),
                    color = Gray2
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyMemePalette(
    modifier: Modifier = Modifier,
    brush: Brush,
    onChangeColor: (BrushColor) -> Unit,
    onChangeAlpha: (Float) -> Unit,
    onChangeWidth: (BrushWidth) -> Unit,
    onClickClose: () -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = Gray8,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(horizontal = 20.dp)
            .padding(bottom = 30.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(bottom = 16.dp)
                    .clickable { onClickClose() },
                painter = painterResource(R.drawable.ic_cancel),
                contentDescription = "",
                tint = Gray5
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BrushWidth.entries.forEach {
                if (it == brush.width) {
                    Column {
                        Icon(
                            modifier = Modifier
                                .height(30.dp)
                                .padding(start = 12.dp),
                            painter = painterResource(it.pen),
                            contentDescription = "",
                            tint = brush.color.color
                        )
                        Image(
                            modifier = Modifier
                                .height(103.dp),
                            painter = painterResource(brush.color.pen),
                            contentDescription = "",
                            contentScale = ContentScale.FillHeight
                        )
                    }
                } else {
                    Box(modifier = Modifier)
                }
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Gray7
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = (12.5).dp)
                    .height(22.dp)
                    .background(
                        color = Gray7,
                        shape = RoundedCornerShape(34.dp)
                    )
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BrushWidth.entries.forEach {
                    Box(
                        modifier = Modifier
                            .size(it.selectorWidth)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                    )
                }
            }
            Slider(
                modifier = Modifier.padding(horizontal = 18.dp),
                value = brush.width.ordinal.toFloat(),
                onValueChange = {
                    println("yeoon ju : $it")
                    val width = BrushWidth.entries[it.toInt()]
                    onChangeWidth(width)
                },
                track = { sliderState ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp)
                    )
                },
                thumb = { sliderState ->
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .background(White, CircleShape)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "${brush.width.ordinal + 1}",
                            style = Caption.toTextStyle(),
                            color = Gray7
                        )
                    }
                },
                steps = 3,
                valueRange = 0f..4f
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = (11.5).dp)
                    .height(26.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(26.dp),
                    painter = painterResource(R.drawable.img_checkboard),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(26.dp)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(
                                    brush.color.color.copy(alpha = 0f),
                                    brush.color.color
                                ),
                                start = Offset(0f, 0f),
                                end = Offset.Infinite
                            ),
                            shape = RoundedCornerShape(34.dp)
                        )
                )
            }
            Slider(
                value = brush.alpha * 100f,
                onValueChange = { onChangeAlpha(it) },
                track = { sliderState ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp)
                    )
                },
                thumb = { sliderState ->
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .background(White, CircleShape)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "${(brush.alpha * 100f).toInt()}",
                            style = Caption.toTextStyle(),
                            color = Gray7
                        )
                    }
                },
                valueRange = 0f..100f
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BrushColor.entries.forEach {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(it.color, CircleShape)
                        .clickable { onChangeColor(it) }
                )
            }
        }
    }
}