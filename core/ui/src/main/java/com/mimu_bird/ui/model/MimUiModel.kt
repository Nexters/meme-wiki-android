package com.mimu_bird.ui.model

import com.mimu_bird.domain.model.meme.MemeModel

/**
 * 밈 정보 Ui Model
 */
data class MimUiModel(
    val id: Int, // 밈 식별자
    val imageUrl: String, // 밈 이미지 Url
    val year: String, // 유행 연도
    val title: String, // 밈 제목
    val tags: List<String>, // 해시 태그
    val usage: String, // 용도
    val source: String // 유래
)

fun MemeModel.toUiModel(): MimUiModel {
    return MimUiModel(
        id = id,
        imageUrl = imageUrl,
        year = year,
        title = title,
        tags = hashTags,
        usage = usage,
        source = origin
    )
}

