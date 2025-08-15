package com.mimu_bird.data.util

import com.mimu_bird.domain.model.category.CategoryModel
import com.mimu_bird.network.model.response.category.CategoryResponse

fun CategoryResponse.toModel(): CategoryModel {
    return CategoryModel(
        id = categoryId,
        name = name,
        imageUrl = imageUrl
    )
}