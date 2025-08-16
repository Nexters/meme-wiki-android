package com.mimu_bird.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.theme.Display1_Point
import com.mimu_bird.designsystem.typography.toTextStyle
import kotlinx.coroutines.delay

@Composable
fun MemeTimer(initialHours: Int, initialMinutes: Int, initialSeconds: Int, modifier: Modifier) {
    // initial 값들을 remember로 캐시하여 불필요한 재계산 방지
    val initialTime by remember(initialHours, initialMinutes, initialSeconds) {
        mutableStateOf(initialHours * 3600 + initialMinutes * 60 + initialSeconds)
    }
    
    var totalSeconds by remember { mutableStateOf(initialTime) }

    // initialTime이 변경될 때만 totalSeconds 업데이트
    LaunchedEffect(initialTime) {
        totalSeconds = initialTime
    }

    LaunchedEffect(Unit) {
        while (totalSeconds > 0) {
            delay(1000) // 1초 대기
            totalSeconds--
        }
    }

    // 시간을 HH:MM:SS 형식으로 변환
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    // 2자리 숫자로 포맷팅
    val formattedHours = String.format("%02d", hours)
    val formattedMinutes = String.format("%02d", minutes)
    val formattedSeconds = String.format("%02d", seconds)

    Row(modifier = modifier.fillMaxWidth().background(Color.Black)) {
        Text(text = formattedHours, style = Display1_Point.toTextStyle(), color = Color.White)
        Text(":", style = Display1_Point.toTextStyle(), color = Color.White)
        Text(text = formattedMinutes, style = Display1_Point.toTextStyle(), color = Color.White)
        Text(":", style = Display1_Point.toTextStyle(), color = Color.White)
        Text(text = formattedSeconds, style = Display1_Point.toTextStyle(), color = Color.White)
    }
}

@Preview
@Composable
fun MemeTimerPreview() {
    MemeTimer(24, 0, 0, modifier = Modifier.padding(start = 14.dp))
}
