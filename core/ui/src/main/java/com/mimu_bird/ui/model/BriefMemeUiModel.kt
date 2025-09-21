package com.mimu_bird.ui.model

import com.mimu_bird.domain.model.meme.MemeDetailModel

data class BriefMemeUiModel(
    val id: String, // 밈 식별자
    val imageUrl: String, // 밈 이미지 Url
    val title: String, // 밈 타이틀
    val rank: Int, //밈 순위
)

fun MemeDetailModel.toBrief(): BriefMemeUiModel {
    return BriefMemeUiModel(
        id = id.toString(),
        imageUrl = imgUrl,
        title = title,
        rank = 0
    )
}