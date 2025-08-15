package com.mimu_bird.data.util

import com.mimu_bird.domain.model.category.BriefMemeModel
import com.mimu_bird.network.model.response.category.CategoryNetworkModel

fun CategoryNetworkModel.toModel(): BriefMemeModel = BriefMemeModel(
    id = id,
    name = name,
    imageUrl = imageUrl
)
