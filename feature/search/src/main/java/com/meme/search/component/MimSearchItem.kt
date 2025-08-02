package com.meme.search.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.meme.search.R
import com.mimu_bird.designsystem.theme.Body1
import com.mimu_bird.designsystem.theme.Body_Long2_Point
import com.mimu_bird.designsystem.theme.Caption
import com.mimu_bird.designsystem.theme.Display1
import com.mimu_bird.designsystem.theme.GradientPalette
import com.mimu_bird.designsystem.theme.Headline2
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.MimUiModel

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
    val gradient = rememberSaveable { GradientPalette.entries.random() }

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .then(
                    if (isKeyword) Modifier.height(240.dp)
                    else Modifier.aspectRatio(1f)
                )
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
                                0.0f to Color.Black.copy(alpha = 0f),
                                0.65f to Color.Black.copy(alpha = 0.2f),
                                1.0f to Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    )
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
                                0.85f to gradient.background.copy(alpha = 0.5f),
                                1.0f to gradient.background.copy(alpha = 0.8f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .then(
                        if (isKeyword) Modifier.padding(horizontal = 14.dp, vertical = 20.dp)
                        else Modifier.padding(8.dp)
                    ),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = meme.title,
                    style = if (isKeyword) Display1.toTextStyle()
                    else Headline2.toTextStyle(),
                    color = Color.White
                )
                Text(
                    text = meme.tags.joinToString(" "),
                    style = if (isKeyword) Subhead2.toTextStyle()
                    else Caption.toTextStyle(),
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
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                text = "${meme.year}",
                style = Body_Long2_Point.toTextStyle(),
                color = Color(0xFF1F2021)
            )
        }
        if (isKeyword) {
            MimSearchInfo(
                modifier = Modifier.padding(top = 12.dp),
                icon = R.drawable.img_search_usage,
                title = "용도",
                desc = meme.usage
            )
            MimSearchInfo(
                modifier = Modifier.padding(top = 8.dp),
                icon = R.drawable.img_search_source,
                title = "유래",
                desc = meme.source
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun MimSearchInfo(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
    desc: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF313133),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 10.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(22.dp),
            painter = painterResource(icon),
            contentDescription = title
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = title,
            style = Subhead2.toTextStyle(),
            color = Color(0xFFFBFBFB)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .basicMarquee(animationMode = MarqueeAnimationMode.Immediately),
                text = desc,
                style = Body1.toTextStyle(),
                color = Color(0xFFD4D6D9),
                maxLines = 1
            )
        }
    }
}