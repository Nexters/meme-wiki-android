package com.mimu_bird.domain.usercase.category

import androidx.paging.PagingData
import com.mimu_bird.domain.model.meme.MemeModel
import com.mimu_bird.domain.repository.category.CategoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 특정 카테고리의 밈 정보 요청 UseCase
 */
@ViewModelScoped
class GetMemesOfCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){
    /**
     * @param categoryId 요청하는 카테고리 식별자
     */
    suspend operator fun invoke(
        categoryId: Int
    ): Flow<PagingData<MemeModel>> {
        return categoryRepository.getMemesOfCategory(
            categoryId = categoryId
        )
    }
}