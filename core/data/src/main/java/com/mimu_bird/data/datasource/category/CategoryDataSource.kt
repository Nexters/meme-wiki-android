package com.mimu_bird.data.datasource.category

import com.mimu_bird.network.api.CategoryService
import com.mimu_bird.network.model.response.category.CategoryResponse
import com.mimu_bird.network.model.response.search.MemeSearchResponse
import com.mimu_bird.network.util.ApiCallUtil
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 밈 카테고리 관리 Data Source
 */
@Singleton
class CategoryDataSource @Inject constructor(
    private val categoryService: CategoryService
){
    /**
     * 밈 카테고리 요청
     */
    suspend fun getCategories(): List<CategoryResponse> {
        return ApiCallUtil {
            categoryService.getCategories()
        }
    }

    /**
     * 특정 카테고리의 밈 정보 요청
     * @param next cursor
     * @param limit 페이지 당 결과 개수
     */
    suspend fun getMemesOfCategory(
        categoryId: Int,
        next: Int?,
        limit: Int
    ): MemeSearchResponse {
        return ApiCallUtil {
            categoryService.getMemesOfCategory(
                categoryId = "$categoryId",
                next = next,
                limit = limit
            )
        }
    }
}