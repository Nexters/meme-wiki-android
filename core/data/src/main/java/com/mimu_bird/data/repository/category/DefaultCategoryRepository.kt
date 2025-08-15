package com.mimu_bird.data.repository.category

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mimu_bird.data.datasource.category.CategoryDataSource
import com.mimu_bird.data.repository.search.MemePagingSource
import com.mimu_bird.data.util.toModel
import com.mimu_bird.domain.model.category.CategoryModel
import com.mimu_bird.domain.model.meme.MemeModel
import com.mimu_bird.domain.repository.category.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 카테고리 관련 로직 관리 Repository
 */
class DefaultCategoryRepository @Inject constructor(
    private val categoryDataSource: CategoryDataSource
) : CategoryRepository {
    // 카테고리 리스트 요청
    override suspend fun getCategories(): List<CategoryModel> {
        return categoryDataSource.getCategories().map {
            it.toModel()
        }
    }

    override suspend fun getMemesOfCategory(
        categoryId: Int
    ): Flow<PagingData<MemeModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = MemePagingSource.LIMIT_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MemePagingSource(
                    query = "",
                    getMemes = { next, query, limit ->
                        categoryDataSource.getMemesOfCategory(
                            categoryId = categoryId,
                            next = next,
                            limit = limit
                        )
                    }
                )
            }
        ).flow.map {
            it.map { meme -> meme.toModel() }
        }
    }
}