package com.mimu_bird.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mimu_bird.designsystem.theme.PastelGradientPalette
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.BriefMemeUiModel
import com.mimu_bird.ui.model.TEST_BRIEF_MEME_UI

@Composable
fun ShareMemItem(
    color: PastelGradientPalette,
    item: BriefMemeUiModel,
) {
    Column {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            color.leftTop,
                            color.rightBottom
                        )
                    ),
                    alpha = 1f
                )
                .padding(top = 6.dp, start = 6.dp, end = 6.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "${item.title}",
                style = Subhead2.toTextStyle(),
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 메인 이미지 컨테이너
                Box(
                    modifier = Modifier
                        .aspectRatio(1.44f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = item.imageUrl,
                        contentDescription = "meme image",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ShareMemItemPreview() {
    Column(Modifier.fillMaxSize()) {
        LazyRow(
            Modifier.height(172.dp),
            contentPadding = PaddingValues(end = 11.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            items(count = 5) {
                ShareMemItem(
                    item = TEST_BRIEF_MEME_UI,
                    color = PastelGradientPalette.LIGHT_BLUE
                )
            }
        }
    }
}
