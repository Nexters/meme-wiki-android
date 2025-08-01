package com.meme.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mimu_bird.ui.model.MimUiModel
import com.mimu_bird.ui.model.TEST_MEME

/**
 * 밈 Gradient 색상 팔레트
 */
private enum class GradientPalette(
    val background: Color,
    val chip: Color
) {
    PURPLE(
        background = Color(0xFF7B00FF),
        chip = Color(0xFFF2D6FF)
    ),
    PINK(
        background = Color(0xFFD331B8),
        chip = Color(0xFFFED3F7)
    ),
    VIOLET(
        background = Color(0xFF3A16C9),
        chip = Color(0xFFDBD3FE)
    ),
    LIGHT_BLUE(
        background = Color(0xFF008ECF),
        chip = Color(0xFFC4ECFE)
    ),
    GREEN(
        background = Color(0xFF1ED45A),
        chip = Color(0xFFACFCC7)
    ),
    RED(
        background = Color(0xFFFF4242),
        chip = Color(0xFFFED5D5)
    ),
    YELLOW(
        background = Color(0xFFFEF08C),
        chip = Color(0xFFFEF08C)
    )
}

/**
 * 밈 검색  결과 item
 * @param meme 밈 정보
 */
@Composable
internal fun MimSearchItem(
    modifier: Modifier = Modifier,
    meme: MimUiModel,
    isKeyword: Boolean
) {
    val gradient = remember { GradientPalette.entries.random() }

    Column (
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = meme.imageUrl,
                contentDescription = meme.title,
                contentScale = ContentScale.Crop
            )
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to gradient.background.copy(alpha = 0f),
                                0.4f to gradient.background.copy(alpha = 0f),
                                0.7f to gradient.background.copy(alpha = 0.2f),
                                1.0f to gradient.background.copy(alpha = 0.5f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = meme.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
                Text(
                    text = meme.tags.joinToString(" ") { "#${it}" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color.White
                )
            }
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = gradient.chip,
                        shape = RoundedCornerShape(100)
                    )
                    .padding(horizontal = 8.dp),
                text = "${meme.year}",
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF1F2021),
                lineHeight = 18.sp
            )
        }

    }
}

@Preview
@Composable
private fun MimSearchItemPreview() {
    MimSearchItem(
        modifier = Modifier.height(240.dp),
        meme = TEST_MEME,
        isKeyword = true
    )
}