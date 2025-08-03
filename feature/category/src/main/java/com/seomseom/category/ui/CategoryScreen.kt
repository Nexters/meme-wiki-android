package com.seomseom.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.ui.model.CategoryUiModel
import com.seomseom.category.component.CategoryTab

@Preview
@Composable
private fun CategoryScreenPreview() {
    CategoryScreen()
}

/**
 * 카테고리 선택 화면
 */
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier
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
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

        }
    }
}