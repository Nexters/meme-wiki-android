package com.example.mymeme.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.theme.Gray11
import com.mimu_bird.designsystem.theme.Gray2
import com.mimu_bird.designsystem.theme.White
import com.mimu_bird.designsystem.typography.toTextStyle

@Composable
fun SaveButton(
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(bottom = 70.dp)
            .width(130.dp)
            .height(60.dp)
            .background(color = Gray11, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = Gray11)
            .clickable { onSave() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_save),
            contentDescription = "저장하기 아이콘",
            tint = White,
            modifier = Modifier.size(24.dp).padding(end = 8.dp)
        )
        Text(
            text = "저장하기",
            style = Body2.toTextStyle(),
            color = Gray2
        )
    }
} 