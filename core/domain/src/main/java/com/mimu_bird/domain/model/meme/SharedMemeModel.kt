package com.mimu_bird.domain.model.meme

/**
 * 공유된 밈 순위 모델
 */
data class SharedMemeModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val nextFetchTime: String
) 