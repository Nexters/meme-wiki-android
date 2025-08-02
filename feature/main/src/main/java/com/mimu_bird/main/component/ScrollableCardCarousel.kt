package com.mimu_bird.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.R


@Composable
fun ScrollableCardCarousel(
    cards: List<Painter>
) {
    val lazyListState = rememberLazyListState()
    val currentPage = remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo

            if (visibleItemsInfo.isNotEmpty()) {
                val viewportCenter = layoutInfo.viewportStartOffset + (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

                // 중앙에 가장 가까운 아이템 찾기
                visibleItemsInfo.minByOrNull { item ->
                    val itemCenter = item.offset + item.size / 2
                    kotlin.math.abs(itemCenter - viewportCenter)
                }?.index ?: 0
            } else {
                0
            }
        }
    }

    Column() {
        LazyRow(
            state = lazyListState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth().height(218.dp)
        ) {
            itemsIndexed(cards) { index, card ->
                CarouselCardItem(
                    painter = card
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(cards.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .background(
                            color = if (index == currentPage.value) Color.White else Color.DarkGray,
                            shape = RoundedCornerShape(6.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun CarouselCardItem(
    painter: Painter,
) {
    androidx.compose.foundation.Image(
        painter = painter,
        contentDescription = "main banner",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(1f)
    )
}

@Preview(showBackground = true)
@Composable
fun ScrollableCardCarouselPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ScrollableCardCarousel(
            cards = listOf(
                painterResource(R.drawable.banner_1),
                painterResource(R.drawable.banner_2),
                painterResource(R.drawable.banner_3)
            )
        )
    }
} 
