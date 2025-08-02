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
import com.mimu_bird.designsystem.theme.Gra_Green
import com.mimu_bird.designsystem.theme.Gra_Light_Blue
import com.mimu_bird.designsystem.theme.Gra_Pink
import com.mimu_bird.designsystem.theme.Gra_Purple
import com.mimu_bird.designsystem.theme.Gra_Red
import com.mimu_bird.designsystem.theme.Gra_Violet
import com.mimu_bird.designsystem.theme.Gra_Yellow
import com.mimu_bird.designsystem.theme.GradientPalette
import com.mimu_bird.designsystem.theme.Gray1
import com.mimu_bird.designsystem.theme.Gray3
import com.mimu_bird.designsystem.theme.Gray8
import com.mimu_bird.designsystem.theme.Gray9
import com.mimu_bird.designsystem.theme.Green90
import com.mimu_bird.designsystem.theme.Headline2
import com.mimu_bird.designsystem.theme.Light_Blue90
import com.mimu_bird.designsystem.theme.Pink90
import com.mimu_bird.designsystem.theme.Purple90
import com.mimu_bird.designsystem.theme.Red90
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.theme.Violet90
import com.mimu_bird.designsystem.theme.Yellow90
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.MimUiModel

/**
 * 밈 Gradient 색상 팔레트
 */
private enum class GradientPalette(
    val background: Color,
    val chip: Color
) {
    PURPLE(background = Gra_Purple, chip = Purple90),
    PINK(background = Gra_Pink, chip = Pink90),
    VIOLET(background = Gra_Violet, chip = Violet90),
    LIGHT_BLUE(background = Gra_Light_Blue, chip = Light_Blue90),
    GREEN(background = Gra_Green, chip = Green90),
    RED(background = Gra_Red, chip = Red90),
    YELLOW(background = Gra_Yellow, chip = Yellow90)
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
                color = Gray9
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
                color = Gray8,
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
            color = Gray1
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
                color = Gray3,
                maxLines = 1
            )
        }
    }
}