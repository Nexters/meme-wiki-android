package com.meme.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.meme.search.R
import com.meme.search.component.MimSearchItem
import com.mimu_bird.designsystem.theme.Body1
import com.mimu_bird.designsystem.theme.Gray1
import com.mimu_bird.designsystem.theme.Gray10
import com.mimu_bird.designsystem.theme.Gray4
import com.mimu_bird.designsystem.theme.Gray5
import com.mimu_bird.designsystem.theme.Gray6
import com.mimu_bird.designsystem.theme.Gray8
import com.mimu_bird.designsystem.theme.Subhead_Long2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.MimUiModel

/**
 * 검색 화면 Screen
 */
@Composable
fun MemeSearchScreen(
    modifier: Modifier = Modifier,
    keyword: String,
    memes: LazyPagingItems<MimUiModel>,
    onChangeKeyword: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(color = Gray10)
    ) {
        MemeSearchBox(
            modifier = Modifier.padding(top = 20.dp, end = 14.dp, bottom = 12.dp, start = 14.dp),
            keyword = keyword,
            onChangeKeyword = onChangeKeyword
        )
        if (memes.itemCount == 0) {
            MemeSearchEmpty()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(
                    if (keyword.isEmpty()) 2
                    else 1
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(
                    if (keyword.isEmpty()) 12.dp
                    else 24.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(count = memes.itemCount, key = { it }) {
                    memes.get(it)?.let { meme ->
                        MimSearchItem(
                            modifier = Modifier
                                .then(
                                    if (keyword.isEmpty()) Modifier
                                    else Modifier.padding(bottom = 24.dp)
                                ),
                            meme = meme,
                            isKeyword = keyword.isNotEmpty()
                        )
                    }
                }
            }
        }
    }
}

/**
 * 검색 결과가 없는 경우 Empty
 */
@Composable
private fun MemeSearchEmpty(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier.size(128.dp),
            painter = painterResource(R.drawable.img_search_empty),
            contentDescription = "검색 결과 없음"
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "검색 결과가 없습니다.\n다시 입력해주세요.",
            style = Body1.toTextStyle(),
            color = Gray5,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.weight(2f))
    }
}

/**
 * 검색 화면 검색어 입력
 * @param keyword 입력된 검색어
 * @param onChangeKeyword 검색어 변경 event
 */
@Composable
private fun MemeSearchBox(
    modifier: Modifier = Modifier,
    keyword: String,
    onChangeKeyword: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Gray8,
                shape = RoundedCornerShape(100)
            )
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_search_20_white),
            contentDescription = "검색 키워드 입력",
            tint = Gray4
        )
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = keyword,
            onValueChange = onChangeKeyword,
            singleLine = true,
            textStyle = Subhead_Long2.toTextStyle().copy(
                color = Gray1
            ),
            decorationBox = { inputField ->
                Box {
                    if (keyword.isEmpty()) {
                        Text(
                            text = "검색어를 입력해주세요,",
                            style = Subhead_Long2.toTextStyle(),
                            color = Gray6
                        )
                    }
                    inputField()
                }
            }
        )
        if (keyword.isNotEmpty()) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onChangeKeyword("") },
                painter = painterResource(com.mimu_bird.designsystem.R.drawable.ic_input_delete_20_dark),
                contentDescription = "키워드 지우기"
            )
        }
    }
}