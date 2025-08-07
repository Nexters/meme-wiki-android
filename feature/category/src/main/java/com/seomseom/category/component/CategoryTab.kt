package com.seomseom.category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.designsystem.theme.Gray5
import com.mimu_bird.designsystem.theme.Gray9
import com.mimu_bird.designsystem.theme.PastelGradientPalette
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.CategoryUiModel

@Preview
@Composable
private fun CategoryTabPreview() {
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
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ScrollableTabRow(
            modifier = Modifier,
            edgePadding = 0.dp,
            selectedTabIndex = selectedTabIndex,
            containerColor = Gray10,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 2.dp,
                    color = Color.White
                )
            },
            divider = {
                HorizontalDivider(
                    color = Gray5,
                    thickness = 2.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, category ->
                val backgroundColor = rememberSaveable(index) {
                    PastelGradientPalette.entries[index % tabs.size]
                }
                Tab(
                    modifier = Modifier
                        .padding(bottom = 14.dp),
                    selected = selectedTabIndex == index,
                    onClick = { onSelectCategory(category) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .then(
                                if (selectedTabIndex != index) Modifier.background(
                                        color = Gray9,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                else Modifier.background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            backgroundColor.leftTop,
                                            backgroundColor.rightBottom
                                        )
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            )
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(38.dp),
                            model = category.imageUrl,
                            contentDescription = category.name
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = category.name,
                        color = if (selectedTabIndex == index) Color.White
                        else Gray5,
                        style = Subhead2.toTextStyle(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = Gray5,
                thickness = 2.dp
            )
        }
    }
}