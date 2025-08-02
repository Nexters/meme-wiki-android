package com.mimu_bird.domain.model.meme

data class MemeModel(
    val id: String, // 밈 식별자
    val title: String, // 밈 제목
    val usage: String, // 밈 용도 정보
    val origin: String, // 밈 유래 정보
    val year: String, // 유행한 연도
    val imageUrl: String, // 이미지 URl
    val hashTags: String // 해시 태그 정보
)
