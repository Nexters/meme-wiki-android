package com.mimu_bird.main.component

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay


@Composable
fun ScrollableCardCarousel(
    cards: List<Painter>,
    onClickPage: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()

    // 자동 스크롤 애니메이션
    LaunchedEffect(cards.size) {
        if (cards.isNotEmpty()) {
            Log.d("ScrollableCardCarousel", "자동 스크롤 시작: cards.size=${cards.size}")
            // 무한 반복을 위한 루프
            repeat(Int.MAX_VALUE) {
                // 각 카드를 순차적으로 스크롤
                for (i in 0 until cards.size) {
                    val scrollAmount = 900f

                    Log.d("ScrollableCardCarousel", "스크롤: ${i}번째 카드, scrollAmount=$scrollAmount")
                    lazyListState.animateScrollBy(
                        value = scrollAmount,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    )

                    delay(400)
                }

                // 스크롤이 끝에 도달하면 처음 위치로 돌아가기
                lazyListState.animateScrollToItem(0)
                delay(1000)
            }
        }
    }

    val currentPage = remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo

            if (visibleItemsInfo.isNotEmpty()) {
                val viewportCenter =
                    layoutInfo.viewportStartOffset + (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

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
            modifier = Modifier
                .fillMaxWidth()
                .height(218.dp),
            userScrollEnabled = false
        ) {
            itemsIndexed(cards) { index, card ->
                CarouselCardItem(
                    modifier = Modifier
                        .clickable { onClickPage(index) },
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
    modifier: Modifier = Modifier,
    painter: Painter,
) {
    androidx.compose.foundation.Image(
        painter = painter,
        contentDescription = "main banner",
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize(1f)
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
            ),
            onClickPage = {}
        )
    }
} 
