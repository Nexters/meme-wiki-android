package com.seomseom.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.Gray1
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.designsystem.theme.Headline1
import com.mimu_bird.designsystem.theme.White
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.component.MimSearchItem
import com.mimu_bird.ui.model.CategoryUiModel
import com.mimu_bird.ui.model.MimUiModel
import com.seomseom.category.business.CategoryViewModel
import com.seomseom.category.component.CategoryTab
import com.seomseom.category.navigation.CategoryNavigationAction
import com.seomseom.category.navigation.CategoryNavigator

/**
 * 카테고리 선택 화면
 */
@Composable
fun CategoryScreen(
    categoryId: Int,
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    navController: NavController,
    navigator: CategoryNavigator? = null
) {
    val categories = viewModel.categories.collectAsState()
    val selectedCategoryIndex = viewModel.selectedCategoryIndex.collectAsState()
    val memes = viewModel.memes.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.fetchCategories(categoryId)
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Gray10)
            .windowInsetsPadding(WindowInsets.systemBars)
    ){
        // 뒤로가기 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left_24),
                contentDescription = "뒤로가기",
                tint = Gray1,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_search_20_white),
                contentDescription = "검색 화면으로 가기",
                tint = Gray1,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navigator?.navigate(
                            CategoryNavigationAction.NavigateSearch
                        )
                    }
            )
        }
        if (categories.value.isNotEmpty()) {
            CategoryTab(
                modifier = Modifier
                    .background(Gray10)
                    .padding(vertical = 20.dp, horizontal = 14.dp),
                tabs = categories.value,
                selectedTabIndex = selectedCategoryIndex.value,
                onSelectCategory = { viewModel.changeSelectedIndex(it) }
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .background(Gray10),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item (
                    span = { GridItemSpan(2) }
                ){
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = categories.value.getOrNull(selectedCategoryIndex.value)?.name ?: "",
                        style = Headline1.toTextStyle(),
                        color = White
                    )
                }
                items(count = memes.itemCount, key = { it }) {
                    memes[it]?.let {
                        MimSearchItem(
                            modifier = Modifier
                                .clickable {
                                    navigator?.navigate(CategoryNavigationAction.NavigateToDetail(it.id))
                                },
                            meme = it,
                            isKeyword = false
                        )
                    }
                }
            }
        }
    }
}