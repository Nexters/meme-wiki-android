package com.mimu_bird.data.util

import com.mimu_bird.domain.model.category.CategoryModel
import com.mimu_bird.network.model.response.category.CategoryNetworkModel

fun CategoryNetworkModel.toModel(): CategoryModel = CategoryModel(
    id = id,
    name = name,
    imageUrl = imageUrl
)