package com.mimu_bird.domain.repository.category

import androidx.paging.PagingData
import com.mimu_bird.domain.model.category.CategoryModel
import com.mimu_bird.domain.model.meme.MemeModel
import kotlinx.coroutines.flow.Flow

/**
 * 카테고리 관련 로직 관려 Repository
 */
interface CategoryRepository {
    /**
     * 카테고리 요청
     */
    suspend fun getCategories(): List<CategoryModel>

    /**
     * 특정 카테고리의 밈 정보 요청
     * @param categoryId 요청하는 카테고리 식별자
     */
    suspend fun getMemesOfCategory(
        categoryId: Int
    ): Flow<PagingData<MemeModel>>
}