package com.mimu_bird.main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.theme.Display2
import com.mimu_bird.designsystem.theme.Display3
import com.mimu_bird.designsystem.theme.PastelGradientPalette
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.main.component.BestMemeView
import com.mimu_bird.main.component.MemeTimer
import com.mimu_bird.main.component.ScrollableCardCarousel
import com.mimu_bird.main.navigation.MainNavigationAction
import com.mimu_bird.main.navigation.MainNavigator
import com.mimu_bird.ui.component.CategoryView
import com.mimu_bird.ui.component.ShareMemItem
import com.mimu_bird.ui.model.TEST_BRIEF_MEME_UI

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navigator: MainNavigator
) {
    val topCategoryColor = listOf(
        PastelGradientPalette.LIGHT_BLUE,
        PastelGradientPalette.PURPLE,
        PastelGradientPalette.MAGENTA,
        PastelGradientPalette.YELLOW
    )
    val bottomCategoryColor = listOf(
        PastelGradientPalette.GREEN,
        PastelGradientPalette.PINK,
        PastelGradientPalette.BLUE,
        PastelGradientPalette.ORANGE
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "로고",
                    tint = Color.White
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "검색화면 이동",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        navigator.navigate(MainNavigationAction.NavigateToSearch)
                    }
                )
            }
            Spacer(Modifier.height(30.dp))
            ScrollableCardCarousel(
                cards = listOf(
                    painterResource(R.drawable.banner_1),
                    painterResource(R.drawable.banner_2),
                    painterResource(R.drawable.banner_3)
                )
            )
            Spacer(Modifier.height(60.dp))
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                repeat(4) {
                    Box(modifier = Modifier.weight(0.17f)) {
                        CategoryView(
                            drawableResId = R.drawable.business_products_magic_rabbit,
                            title = "카테고리 이름",
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .aspectRatio(1f)
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            topCategoryColor[it].leftTop,
                                            topCategoryColor[it].rightBottom
                                        )
                                    ),
                                    alpha = 1f
                                )
                                .padding(15.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .padding(top = 17.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                repeat(4) {
                    Box(modifier = Modifier.weight(0.17f)) {
                        CategoryView(
                            drawableResId = R.drawable.business_products_magic_rabbit,
                            title = "카테고리 이름",
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .aspectRatio(1f)
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            bottomCategoryColor[it].leftTop,
                                            bottomCategoryColor[it].rightBottom
                                        )
                                    ),
                                    alpha = 1f
                                )
                                .padding(15.dp)
                                .clickable {
                                    navigator.navigate(MainNavigationAction.NavigateToCategory)
                                }
                        )
                    }
                }
            }
            Column(
                Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 60.dp)
                    .background(
                        color = Color(0xFFE4E6EB),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "BEST",
                    style = Display3.toTextStyle(),
                    modifier = Modifier.padding(top = 24.dp)
                )
                Text(
                    text = "빠르게 흝어보는 현재 인기 많은 밈",
                    style = Subhead2.toTextStyle(),
                    color = Color(0xFF5C5E61),
                    modifier = Modifier.padding(bottom = 36.dp)
                )
                BestMemeView(
                    items = listOf(
                        TEST_BRIEF_MEME_UI,
                        TEST_BRIEF_MEME_UI,
                        TEST_BRIEF_MEME_UI,
                        TEST_BRIEF_MEME_UI,
                        TEST_BRIEF_MEME_UI,
                        TEST_BRIEF_MEME_UI
                    )
                )
                Spacer(Modifier.height(53.dp))
            }
        }
        item {
            Text(
                text = "지금 공유할 타이밍",
                style = Display2.toTextStyle(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, bottom = 18.dp, start = 14.dp)
            )
            Text(
                text = "지금 공유할 타이밍",
                style = Body2.toTextStyle(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp, start = 14.dp)
            )
            MemeTimer(24, 0, 0, modifier = Modifier.padding(start = 14.dp, bottom = 20.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(172.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                flingBehavior = rememberSnapFlingBehavior(lazyListState = rememberLazyListState())
            ) {
                items(count = 5) { index ->
                    ShareMemItem(
                        item = TEST_BRIEF_MEME_UI,
                        color = topCategoryColor.get(index)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            // 아래쪽 LazyRow - 오른쪽으로 스크롤 (reverseLayout = true)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(172.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                reverseLayout = true, // 오른쪽으로 스크롤
                flingBehavior = rememberSnapFlingBehavior(lazyListState = rememberLazyListState())
            ) {
                items(count = 5) { index ->
                    ShareMemItem(
                        item = TEST_BRIEF_MEME_UI,
                        color = bottomCategoryColor.get(index)
                    )
                }
            }
            Spacer(Modifier.height(100.dp))
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    // Preview에서는 실제 navigator가 필요하지 않으므로 빈 구현체를 사용
    MainScreen(
        navigator = object : MainNavigator {
            override fun navigate(action: MainNavigationAction) {
                // Preview에서는 아무것도 하지 않음
            }
        }
    )
}
