package com.mimu_bird.domain.model.meme

import com.mimu_bird.domain.model.category.BriefMemeModel

/**
 * 공유된 밈 순위 모델
 */
data class SharedMemeModel(
    val memes: List<BriefMemeModel>,
    val nextFetchTime: String
)

