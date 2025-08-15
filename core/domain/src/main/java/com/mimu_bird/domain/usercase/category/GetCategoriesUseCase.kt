package com.mimu_bird.domain.usercase.category

import com.mimu_bird.domain.model.category.CategoryModel
import com.mimu_bird.domain.repository.category.CategoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * 카테고리 리스트 요청 UseCase
 */
@ViewModelScoped
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke(): Result<List<CategoryModel>> {
        return runCatching {
            categoryRepository.getCategories()
        }
    }
}