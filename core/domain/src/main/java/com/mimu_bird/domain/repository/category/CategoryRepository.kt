package com.mimu_bird.domain.repository.category

import com.mimu_bird.domain.model.category.BriefMemeModel

interface CategoryRepository {
    suspend fun getCategories(): List<BriefMemeModel>
}