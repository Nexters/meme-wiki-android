package com.seomseom.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.mimu_bird.designsystem.theme.Gray1
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.designsystem.theme.Headline1
import com.mimu_bird.designsystem.theme.White
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.component.MimSearchItem
import com.mimu_bird.ui.model.CategoryUiModel
import com.mimu_bird.ui.model.MimUiModel
import com.seomseom.category.component.CategoryTab
import com.seomseom.category.navigation.CategoryNavigator

@Preview
@Composable
private fun CategoryScreenPreview() {
    CategoryScreen(
        categoryId = 0,
        navigator = object : CategoryNavigator {
            override fun navigate(action: com.seomseom.category.navigation.CategoryNavigationAction) {
                // Preview에서는 아무것도 하지 않음
            }
        }
    )
}

/**
 * 카테고리 선택 화면
 */
@Composable
fun CategoryScreen(
    categoryId: Int,
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    navigator: CategoryNavigator? = null
) {
    val categories = viewModel.categories.collectAsState()
    val selectedCategoryIndex = viewModel.selectedCategoryIndex.collectAsState()
    val memes = viewModel.memes.collectAsLazyPagingItems()

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Gray10)
    ){
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
                            meme = it,
                            isKeyword = false
                        )
                    }
                }
            }
        }
    }
}