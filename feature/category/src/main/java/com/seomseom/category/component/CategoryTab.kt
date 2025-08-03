package com.seomseom.category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.CategoryUiModel

@Preview
@Composable
private fun CategoryTabPreview() {
    CategoryTab(
        modifier = Modifier,
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
}

/**
 * 카테고리 화면 상단 탭
 * @param tabs 카테고리 종류
 * @param selectedTabIndex 현재 선택된 카테고리 index
 * @param onSelectCategory 카테고리 선택 event
 */
@Composable
internal fun CategoryTab(
    modifier: Modifier = Modifier,
    tabs: List<CategoryUiModel>,
    selectedTabIndex: Int,
    onSelectCategory: (CategoryUiModel) -> Unit
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 2.5.dp,
                color = Color.White
            )
        },
        divider = {
            HorizontalDivider()
        }
    ) {
        tabs.forEachIndexed { index, category ->
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = category.name,
                    color = Color.White,
                    style = Subhead2.toTextStyle()
                )
            }
        }
    }
}