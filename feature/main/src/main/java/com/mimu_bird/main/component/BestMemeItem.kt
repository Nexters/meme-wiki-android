package com.mimu_bird.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mimu_bird.designsystem.theme.PastelGradientPalette
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.BriefMemeUiModel
import com.mimu_bird.ui.model.TEST_BRIEF_MEME_UI

@Composable
fun BestMemeView(
    items: List<BriefMemeUiModel>,
    onClickMeme: (String) -> Unit
) {
    val colors = listOf(
        PastelGradientPalette.PINK,
        PastelGradientPalette.MAGENTA,
        PastelGradientPalette.YELLOW,
        PastelGradientPalette.LIGHT_BLUE,
        PastelGradientPalette.PURPLE,
        PastelGradientPalette.GREEN
    )
    if (!items.isEmpty()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
        ) {
            repeat(3) { rowIndex ->
                Row(
                    Modifier
                        .fillMaxWidth(1f)
                        .padding(bottom = 11.dp)
                ) {
                    Box(Modifier.weight(0.5f)) {
                        BestMemeItem(
                            color = colors[rowIndex * 2].rightBottom,
                            item = items[rowIndex * 2],
                            onClickMeme = onClickMeme
                        )
                    }
                    Spacer(Modifier.width(11.dp))
                    Box(Modifier.weight(0.5f)) {
                        BestMemeItem(
                            color = colors[rowIndex * 2 + 1].rightBottom,
                            item = items[rowIndex * 2 + 1],
                            onClickMeme = onClickMeme
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BestMemeItem(
    color: Color,
    item: BriefMemeUiModel,
    onClickMeme: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .aspectRatio(0.82f)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                item.id.let(onClickMeme)
            }
    ) {
        Box(modifier = Modifier.weight(1f)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = item.imageUrl,
                contentDescription = "best meme item",
                contentScale = ContentScale.Crop
            )
            Box(Modifier.padding(top = 8.dp, start = 8.dp)) {
                Text(
                    text = "${item.rank}위",
                    style = Subhead2.toTextStyle(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(color = color, shape = RoundedCornerShape(6.dp))
                        .height(24.dp)
                        .width(38.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to color.copy(alpha = 0f),
                                0.4f to color.copy(alpha = 0f),
                                0.7f to color.copy(alpha = 0.2f),
                                1.0f to color.copy(alpha = 0.5f)
                            )
                        )
                    )
            )
        }
        Text(
            text = item.title,
            style = Subhead2.toTextStyle(),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = color)
                .height(36.dp)
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(start = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun BestMemeItemPreview() {
    val colors = listOf(
        PastelGradientPalette.PINK,
        PastelGradientPalette.MAGENTA,
        PastelGradientPalette.YELLOW,
        PastelGradientPalette.LIGHT_BLUE,
        PastelGradientPalette.PURPLE,
        PastelGradientPalette.GREEN
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(bottom = 11.dp)
        ) {
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(
                    color = PastelGradientPalette.PINK.rightBottom,
                    item = TEST_BRIEF_MEME_UI,
                    onClickMeme = {}
                )
            }
            Spacer(Modifier.width(11.dp))
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(
                    color = PastelGradientPalette.MAGENTA.rightBottom,
                    item = TEST_BRIEF_MEME_UI,
                    onClickMeme = {}
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(bottom = 11.dp)
        ) {
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(
                    color = PastelGradientPalette.YELLOW.rightBottom,
                    item = TEST_BRIEF_MEME_UI,
                    onClickMeme = {}
                )
            }
            Spacer(Modifier.width(11.dp))
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(
                    color = PastelGradientPalette.LIGHT_BLUE.rightBottom,
                    item = TEST_BRIEF_MEME_UI,
                    onClickMeme = {}
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(bottom = 11.dp)
        ) {
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(
                    color = PastelGradientPalette.PURPLE.rightBottom,
                    item = TEST_BRIEF_MEME_UI,
                    onClickMeme = {}
                )
            }
            Spacer(Modifier.width(11.dp))
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(
                    color = PastelGradientPalette.GREEN.rightBottom,
                    item = TEST_BRIEF_MEME_UI,
                    onClickMeme = {}
                )
            }
        }
    }
}
