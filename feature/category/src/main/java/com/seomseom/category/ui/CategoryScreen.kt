package com.seomseom.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.designsystem.theme.Headline1
import com.mimu_bird.designsystem.theme.White
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.component.MimSearchItem
import com.mimu_bird.ui.model.CategoryUiModel
import com.mimu_bird.ui.model.MimUiModel
import com.mimu_bird.ui.model.TEST_BRIEF_MEME_UI
import com.seomseom.category.component.CategoryTab
import com.seomseom.category.navigation.CategoryNavigator

@Preview
@Composable
private fun CategoryScreenPreview() {
    CategoryScreen(
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
    modifier: Modifier = Modifier,
    navigator: CategoryNavigator? = null
) {
    Column (
        modifier = modifier
    ){
        CategoryTab(
            modifier = Modifier
                .background(Gray10)
                .padding(vertical = 20.dp, horizontal = 14.dp),
            tabs = (0..2).map {
                CategoryUiModel(
                    id = it,
                    name = "카테고리 $it",
                    imageUrl = ""
                )
            },
            selectedTabIndex = 2,
            onSelectCategory = {}
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
                    text = "선택된 카테고리 이름",
                    style = Headline1.toTextStyle(),
                    color = White
                )
            }
            items(count = 20, key = { it }) {
                MimSearchItem(
                    meme = MimUiModel(
                        id = it,
                        imageUrl = "",
                        year = "2025",
                        title = "밈 $it",
                        tags = emptyList(),
                        usage = "",
                        source = ""
                    ),
                    isKeyword = false
                )
            }
        }
    }
}