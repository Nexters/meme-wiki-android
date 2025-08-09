package com.mimu_bird.data.repository.category

import com.mimu_bird.data.datasource.category.CategoryDataSource
import com.mimu_bird.data.util.toModel
import com.mimu_bird.domain.model.category.CategoryModel
import com.mimu_bird.domain.repository.category.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCategoryRepository @Inject constructor(
    private val categoryDataSource: CategoryDataSource
): CategoryRepository {
    override suspend fun getCategories(): List<CategoryModel> {
        return categoryDataSource.getCategories().map { it.toModel() }
    }
}