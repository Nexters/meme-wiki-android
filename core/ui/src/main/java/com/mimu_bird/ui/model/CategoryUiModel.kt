package com.mimu_bird.ui.model

import com.mimu_bird.domain.model.category.BriefMemeModel
import com.mimu_bird.domain.model.category.CategoryModel

/**
 * 카테고리 UI 정보
 */
data class CategoryUiModel(
    val id: Int, // 카테고리 식별자
    val name: String, // 카테고리명
    val imageUrl: String // 카테고리 이미지
)

fun BriefMemeModel.toUiModel(): CategoryUiModel {
    return CategoryUiModel(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}