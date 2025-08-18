package com.example.mymeme.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.theme.Blue50
import com.mimu_bird.designsystem.theme.Body1
import com.mimu_bird.designsystem.theme.Gray3
import com.mimu_bird.designsystem.theme.Subhead1
import com.mimu_bird.designsystem.typography.toTextStyle

@Composable
fun SaveResultDialog(
    isSuccess: Boolean,
    onDismiss: () -> Unit
) {
    val dialogHeight = if (isSuccess) 110.dp else 138.dp
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .width(260.dp)
            .height(dialogHeight)
            .background(
                color = Color.White.copy(alpha = 0.9f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = if (isSuccess) "저장이 완료되었습니다." else "저장 중 오류가 발생했습니다.",
                color = Color(0xFF242424),
                textAlign = TextAlign.Center,
                style = Subhead1.toTextStyle(),
            )
            if (!isSuccess) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "다시 시도해주세요.",
                    color = Color(0xFF242424),
                    textAlign = TextAlign.Center,
                    style = Body1.toTextStyle()
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // 구분선
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Gray3)
            )

            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(indication = null, interactionSource = interactionSource) {},
            ) {
                Text(
                    text = "확인",
                    textAlign = TextAlign.Center,
                    style = Subhead1.toTextStyle(),
                    color = Blue50
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview(name = "저장 성공 다이얼로그", showBackground = true)
@Composable
fun SaveResultDialogSuccessPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        SaveResultDialog(
            isSuccess = true,
            onDismiss = {}
        )
    }
}

@Preview(name = "저장 실패 다이얼로그", showBackground = true)
@Composable
fun SaveResultDialogFailurePreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        SaveResultDialog(
            isSuccess = false,
            onDismiss = {}
        )
    }
}

@Preview(name = "다이얼로그 비교", showBackground = true)
@Composable
fun SaveResultDialogComparisonPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
    ) {
        // 성공 다이얼로그
        SaveResultDialog(
            isSuccess = true,
            onDismiss = {}
        )

        // 실패 다이얼로그
        SaveResultDialog(
            isSuccess = false,
            onDismiss = {}
        )
    }
}