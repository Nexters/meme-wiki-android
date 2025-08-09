package com.mimu_bird.domain.usercase.category

import com.mimu_bird.domain.model.category.CategoryModel
import com.mimu_bird.domain.repository.category.CategoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): List<CategoryModel> {
        return categoryRepository.getCategories()
    }
}