package com.example.mymeme.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.theme.Body1
import com.mimu_bird.designsystem.theme.Gray3
import com.mimu_bird.designsystem.theme.Gray8
import com.mimu_bird.designsystem.typography.toTextStyle

@Composable
fun TextSizeDialog(
    selectedSize: Int,
    onSizeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val sizeList = listOf<Int>(10, 12, 14, 18, 24, 36, 48, 64, 72, 96)

    Column(
        modifier = modifier
            .size(width = 210.dp, height = 320.dp)
            .background(Color.White.copy(alpha = 0.94f), shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.94f),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        // 텍스트 크기 목록
        LazyColumn(
        ) {
            items(sizeList.size) { index ->
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .clickable { onSizeSelected(sizeList[index]) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${sizeList[index]} pt",
                            style = Body1.toTextStyle(),
                            color = Gray8,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }

                    // 마지막 아이템이 아닌 경우에만 Spacer 표시
                    if (index < sizeList.size - 1) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Gray3)
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TextSizeDialogPreview() {
    TextSizeDialog(
        selectedSize = 18,
        onSizeSelected = {}
    )
}