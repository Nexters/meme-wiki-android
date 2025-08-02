package com.mimu_bird.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun BestMemeView(items: List<BriefMemeUiModel>) {
    val colors = listOf(
        PastelGradientPalette.PINK,
        PastelGradientPalette.MAGENTA,
        PastelGradientPalette.YELLOW,
        PastelGradientPalette.LIGHT_BLUE,
        PastelGradientPalette.PURPLE,
        PastelGradientPalette.GREEN
    )

    Column(Modifier.fillMaxSize().padding(horizontal = 14.dp)) {
        repeat(3) { rowIndex ->
            Row(Modifier.fillMaxWidth(1f).padding(bottom = 11.dp)) {
                Box(Modifier.weight(0.5f)) {
                    BestMemeItem(
                        color = colors[rowIndex * 2].rightBottom,
                        item = items[rowIndex * 2]
                    )
                }
                Spacer(Modifier.width(11.dp))
                Box(Modifier.weight(0.5f)) {
                    BestMemeItem(
                        color = colors[rowIndex * 2 + 1].rightBottom,
                        item = items[rowIndex * 2 + 1]
                    )
                }
            }
        }
    }
}

@Composable
private fun BestMemeItem(color: Color, item: BriefMemeUiModel) {
    Box(Modifier.padding(top = 8.dp, start = 8.dp)) {
        Text(
            text = "${item.rank}위",
            style = Subhead2.toTextStyle(),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.background(color = color, shape = RoundedCornerShape(6.dp))
                .height(24.dp)
                .width(38.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
        )
    }
    Column(modifier = Modifier.aspectRatio(0.82f).clip(RoundedCornerShape(12.dp))) {
        Box(modifier = Modifier.weight(1f)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = item.imageUrl,
                contentDescription = "best meme item",
                contentScale = ContentScale.Crop
            )
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
            textAlign = TextAlign.Start,
            modifier = Modifier
                .background(color = color)
                .height(36.dp)
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(start = 16.dp)
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

    Column(Modifier.fillMaxSize().padding(horizontal = 14.dp)) {
        Row(Modifier.fillMaxWidth(1f).padding(bottom = 11.dp)) {
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(color = PastelGradientPalette.PINK.rightBottom, item = TEST_BRIEF_MEME_UI)
            }
            Spacer(Modifier.width(11.dp))
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(color = PastelGradientPalette.MAGENTA.rightBottom, item = TEST_BRIEF_MEME_UI)
            }
        }
        Row(Modifier.fillMaxWidth(1f).padding(bottom = 11.dp)) {
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(color = PastelGradientPalette.YELLOW.rightBottom, item = TEST_BRIEF_MEME_UI)
            }
            Spacer(Modifier.width(11.dp))
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(color = PastelGradientPalette.LIGHT_BLUE.rightBottom, item = TEST_BRIEF_MEME_UI)
            }
        }
        Row(Modifier.fillMaxWidth(1f).padding(bottom = 11.dp)) {
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(color = PastelGradientPalette.PURPLE.rightBottom, item = TEST_BRIEF_MEME_UI)
            }
            Spacer(Modifier.width(11.dp))
            Box(Modifier.weight(0.5f)) {
                BestMemeItem(color = PastelGradientPalette.GREEN.rightBottom, item = TEST_BRIEF_MEME_UI)
            }
        }
    }
}
