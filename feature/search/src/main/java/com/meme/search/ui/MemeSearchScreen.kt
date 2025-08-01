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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meme.search.R
import com.meme.search.component.MimSearchItem
import com.mimu_bird.designsystem.theme.Body1
import com.mimu_bird.designsystem.theme.Subhead_Long2
import com.mimu_bird.designsystem.typography.toTextStyle
import com.mimu_bird.ui.model.TEST_MEME

/**
 * 검색 화면 Screen
 */
@Composable
fun MemeSearchScreen(
    modifier: Modifier = Modifier
) {
    // TODO ViewModel 로 이동 예정
    val keyword = remember { mutableStateOf("퉤사") }
    val memes = remember { (0..20).toList() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(color = Color(0xFF121212))
    ) {
        MemeSearchBox(
            modifier = Modifier.padding(top = 20.dp, end = 14.dp, bottom = 12.dp, start = 14.dp),
            keyword = keyword.value,
            onChangeKeyword = { keyword.value = it }
        )
        if (memes.isEmpty()) {
            MemeSearchEmpty()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(
                    if (keyword.value.isEmpty()) 2
                    else 1
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(
                    if (keyword.value.isEmpty()) 12.dp
                    else 24.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = memes, key = { it }) {
                    MimSearchItem(
                        modifier = Modifier
                            .then(
                                if (keyword.value.isEmpty()) Modifier
                                else Modifier.padding(bottom = 24.dp)
                            ),
                        meme = TEST_MEME,
                        isKeyword = keyword.value.isNotEmpty()
                    )
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
            color = Color(0xFF8C8F93),
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
                color = Color(0xFF313133),
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
            tint = Color(0xFFB6B9BD)
        )
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = keyword,
            onValueChange = onChangeKeyword,
            singleLine = true,
            textStyle = Subhead_Long2.toTextStyle().copy(
                color = Color(0xFFFBFBFB)
            ),
            decorationBox = { inputField ->
                Box {
                    if (keyword.isEmpty()) {
                        Text(
                            text = "검색어를 입력해주세요,",
                            style = Subhead_Long2.toTextStyle(),
                            color = Color(0xFF7E8185)
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

@Preview
@Composable
private fun MemeSearchScreenPreview() {
    MemeSearchScreen()
}