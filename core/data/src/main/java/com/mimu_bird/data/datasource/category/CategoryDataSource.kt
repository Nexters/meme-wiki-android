package com.mimu_bird.data.datasource.category

import com.mimu_bird.network.api.CategoryService
import com.mimu_bird.network.model.response.category.CategoryNetworkModel
import com.mimu_bird.network.util.ApiCallUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryDataSource @Inject constructor(
    private val categoryService: CategoryService
) {
    suspend fun getCategories(): List<CategoryNetworkModel> {
        return ApiCallUtil {
            categoryService.getCategories()
        }
    }
}