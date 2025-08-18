package com.mimu_bird.main.ui


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.theme.Display1
import com.mimu_bird.designsystem.theme.Display2
import com.mimu_bird.designsystem.theme.Display3
import com.mimu_bird.designsystem.theme.PastelGradientPalette
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.main.business.MainViewModel
import com.mimu_bird.main.component.BestMemeView
import com.mimu_bird.main.component.MemeTimer
import com.mimu_bird.main.component.ScrollableCardCarousel
import com.mimu_bird.main.navigation.MainNavigationAction
import com.mimu_bird.main.navigation.MainNavigator
import com.mimu_bird.ui.component.CategoryView
import com.mimu_bird.ui.component.ShareMemItem
import com.mimu_bird.ui.model.BriefMemeUiModel
import com.mimu_bird.ui.model.DUMMY_SHARED_MEMES
import kotlinx.coroutines.delay

/**
 * 자동으로 연속 스크롤되는 LazyRow 컴포넌트
 */
@Composable
private fun AutoScrollingLazyRow(
    items: List<BriefMemeUiModel>,
    colors: List<PastelGradientPalette>,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false
) {
    val listState = rememberLazyListState()
    LaunchedEffect(items.size) {
        if (items.isNotEmpty() && !items.first().id.startsWith("dummy")) {
            Log.d(
                "AutoScrollingLazyRow",
                "애니메이션 시작: items.size=${items.size}, reverseLayout=$reverseLayout"
            )
            repeat(Int.MAX_VALUE) {
                for (i in 0 until items.size) {
                    listState.animateScrollBy(
                        value = 200f,
                        animationSpec = androidx.compose.animation.core.tween(
                            durationMillis = 800,
                            easing = androidx.compose.animation.core.LinearEasing
                        )
                    )

                    delay(100)
                }

                // 스크롤이 끝에 도달하면 처음 위치로 돌아가기
                listState.animateScrollToItem(0)
                delay(800)
            }
        }
    }


    LazyRow(
        state = listState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        reverseLayout = reverseLayout,
        userScrollEnabled = false
    ) {
        items(items.size) { index ->
            ShareMemItem(
                item = items[index],
                color = colors.getOrElse(index) { colors.first() }
            )
        }
    }
}

@Composable
fun MainScreen(
    navigator: MainNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val topRatedMemes by viewModel.topRatedMemes.collectAsState()
    val sharedMemes by viewModel.sharedMemes.collectAsState()
    val timeUntilNextUpdate by viewModel.timeUntilNextUpdate.collectAsState()

    // 상위 5개와 하위 5개로 분리 (서버 데이터가 없으면 더미 데이터 사용)
    val top5Memes = if (sharedMemes.isNotEmpty()) {
        sharedMemes.take(5).map { sharedMeme ->
            BriefMemeUiModel(
                id = sharedMeme.id.toString(),
                imageUrl = sharedMeme.imageUrl,
                title = sharedMeme.name,
                rank = 0
            )
        }
    } else {
        DUMMY_SHARED_MEMES.take(5)
    }
    
    val bottom5Memes = if (sharedMemes.isNotEmpty()) {
        sharedMemes.drop((sharedMemes.size - 5).coerceAtLeast(0)).map { sharedMeme ->
            BriefMemeUiModel(
                id = sharedMeme.id.toString(),
                imageUrl = sharedMeme.imageUrl,
                title = sharedMeme.name,
                rank = 0
            )
        }
    } else {
        DUMMY_SHARED_MEMES.take(5)
    }

    val topCategoryColor = listOf(
        PastelGradientPalette.LIGHT_BLUE,
        PastelGradientPalette.PURPLE,
        PastelGradientPalette.MAGENTA,
        PastelGradientPalette.YELLOW
    )
    val topSharedMemeColor1 = listOf(
        PastelGradientPalette.LIGHT_BLUE,
        PastelGradientPalette.YELLOW,
        PastelGradientPalette.PINK,
        PastelGradientPalette.GREEN,
        PastelGradientPalette.PURPLE,
    )
    val topSharedMemeColor2 = listOf(
        PastelGradientPalette.BLUE,
        PastelGradientPalette.MAGENTA,
        PastelGradientPalette.PURPLE,
        PastelGradientPalette.GREEN,
        PastelGradientPalette.PINK,
    )
    val categoryResourceIds = listOf<Int>(
        R.drawable.ic_keyboard,
        R.drawable.ic_umbrella,
        R.drawable.business_products_magic_rabbit,
        R.drawable.ic_relationship
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .windowInsetsPadding(WindowInsets.systemBars)
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
                    painterResource(R.drawable.ic_search_20_white),
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
                    painterResource(R.drawable.banner_0),
                    painterResource(R.drawable.banner_1),
                    painterResource(R.drawable.banner_2),
                    painterResource(R.drawable.banner_3)
                ),
                onClickPage = {
                    if (it == 0) {
                        navigator?.navigate(
                            MainNavigationAction.NavigateToWebView(
                                "https://meme-wiki.net/"
                            )
                        )
                    }
                }
            )
            Spacer(Modifier.height(60.dp))
        }
        item {
            Text(
                text = "뭘 좋아하는지 몰라서\n" +
                        "그냥 다 준비했어\uD83D\uDC40",
                style = Display1.toTextStyle(),
                modifier = Modifier.padding(horizontal = 14.dp),
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .padding(top = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                repeat(4) { index ->
                    val category = categories.getOrNull(index)
                    val categoryTitle = category?.name ?: "카테고리 이름"
                    val categoryId = category?.id ?: 0
                    Box(modifier = Modifier.weight(0.17f)) {
                        CategoryView(
                            drawableResId = categoryResourceIds[index],
                            title = categoryTitle,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .aspectRatio(1f)
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            topCategoryColor[index].leftTop,
                                            topCategoryColor[index].rightBottom
                                        )
                                    ),
                                    alpha = 1f
                                )
                                .padding(15.dp)
                                .clickable {
                                    navigator.navigate(
                                        MainNavigationAction.NavigateToCategory(categoryId)
                                    )
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
                    text = "\uD83D\uDD25인급밈\uD83D\uDD25",
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
                    items = if (topRatedMemes.isNotEmpty()) {
                        topRatedMemes.mapIndexed { index, meme ->
                            BriefMemeUiModel(
                                id = meme.id.toString(),
                                imageUrl = meme.imageUrl,
                                title = meme.title,
                                rank = index + 1
                            )
                        }.take(6)
                    } else {
                        // 서버 데이터가 없을 때 더미 데이터 사용
                        listOf(
                            BriefMemeUiModel("dummy1", "", "인기 밈 1", 1),
                            BriefMemeUiModel("dummy2", "", "인기 밈 2", 2),
                            BriefMemeUiModel("dummy3", "", "인기 밈 3", 3),
                            BriefMemeUiModel("dummy4", "", "인기 밈 4", 4),
                            BriefMemeUiModel("dummy5", "", "인기 밈 5", 5),
                            BriefMemeUiModel("dummy6", "", "인기 밈 6", 6)
                        )
                    },
                    onClickMeme = {
                        // 더미 데이터일 때는 클릭 무시
                        if (!it.startsWith("dummy")) {
                            navigator.navigate(MainNavigationAction.NavigateToDetail(it.toInt()))
                        }
                    }
                )
                Spacer(Modifier.height(53.dp))
            }
        }
        item {
            Text(
                text = "단톡방행 밈 셔틀,\n" +
                        "지금 탑승하세요 \uD83D\uDE82",
                style = Display2.toTextStyle(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, bottom = 4.dp, start = 14.dp)
            )
            Text(
                text = "지금 가장 많이 공유된 미만 골라 실었어요",
                style = Body2.toTextStyle(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp, start = 14.dp)
            )

            // ViewModel에서 계산된 시간 사용
            val (hours, minutes, seconds) = timeUntilNextUpdate

            MemeTimer(
                initialHours = hours,
                initialMinutes = minutes,
                initialSeconds = seconds,
                modifier = Modifier.padding(start = 14.dp, bottom = 20.dp)
            )

            AutoScrollingLazyRow(
                items = top5Memes,
                colors = topSharedMemeColor1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(172.dp),
                reverseLayout = false
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            AutoScrollingLazyRow(
                items = bottom5Memes,
                colors = topSharedMemeColor2,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(172.dp),
                reverseLayout = true
            )
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