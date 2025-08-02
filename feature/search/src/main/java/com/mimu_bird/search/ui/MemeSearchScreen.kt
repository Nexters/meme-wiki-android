package com.mimu_bird.search.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 검색 화면
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MemeSearchScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MemeSearchHeader()
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ){
            MemeSearchBox()
            LazyVerticalStaggeredGrid (
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
            ) {
                items(count = 10, key = { it }) {
                    MemeSearchItem(
                        modifier = Modifier
                            .height((150 + (10 * it)).dp)
                    )
                }
            }
        }
    }
}

/**
 * 검색 화면 상단 Header
 */
@Composable
private fun MemeSearchHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier.padding(12.dp),
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "이전 화면으로 이동"
        )
        Icon(
            modifier = Modifier.padding(12.dp),
            imageVector = Icons.Default.Home,
            contentDescription = "홈 화면으로 이동"
        )
    }
}

/**
 * 검색 화면 키워드 입력칸
 */
@Composable
private fun MemeSearchBox(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(100)
            )
            .padding(vertical = 2.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = "키워드를 입력해주세요"
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.padding(12.dp),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "키워드 지우기"
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            )
        )
    }
}

/**
 * 검색 결과 아이템
 */
@Composable
private fun MemeSearchItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .background(
                    color = Color(0xFF888888),
                    shape = RoundedCornerShape(100)
                ).padding(vertical = 4.dp, horizontal = 8.dp),
            text = "2025",
            color = Color.White
        )
        Column (
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){ 
            Text(
                text = "제목",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "#밈 #태그1 #태그2 #태그3 #태그4",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun MemeSearchScreenPreview() {
    MemeSearchScreen()
}