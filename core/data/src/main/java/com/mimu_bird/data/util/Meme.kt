package com.mimu_bird.data.util

import com.mimu_bird.domain.model.meme.MemeModel
import com.mimu_bird.network.model.response.meme.MemeResponse

fun MemeResponse.toModel(): MemeModel {
    return MemeModel(
        id = id,
        title = title,
        usage = usage,
        origin = origin,
        year = trendPeriod,
        imageUrl = imageUrl,
        hashTags = emptyList()
    )
}