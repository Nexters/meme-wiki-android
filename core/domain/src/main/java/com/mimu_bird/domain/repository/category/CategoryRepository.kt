package com.mimu_bird.domain.repository.category

import com.mimu_bird.domain.model.category.CategoryModel

interface CategoryRepository {
    suspend fun getCategories(): List<CategoryModel>
}