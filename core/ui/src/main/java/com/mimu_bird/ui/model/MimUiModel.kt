package com.mimu_bird.ui.model

/**
 * 밈 정보 Ui Model
 */
data class MimUiModel(
    val id: String, // 밈 식별자
    val imageUrl: String, // 밈 이미지 Url
    val year: Int, // 유행 연도
    val title: String, // 밈 제목
    val tags: List<String> // 해시 태그
)

val TEST_MEME = MimUiModel(
    id = "",
    imageUrl = "https://picsum.photos/seed/picsum/200/300",
    year = 2022,
    title = "죽겠어요",
    tags = listOf("직장인", "해탈짤")
)