package com.seomseom.category.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mimu_bird.domain.usercase.category.GetCategoriesUseCase
import com.mimu_bird.domain.usercase.category.GetMemesOfCategoryUseCase
import com.mimu_bird.ui.model.CategoryUiModel
import com.mimu_bird.ui.model.MimUiModel
import com.mimu_bird.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMemesOfCategoryUseCase: GetMemesOfCategoryUseCase
) : ViewModel() {
    private val _categories = MutableStateFlow<List<CategoryUiModel>>(emptyList())
    val categories: StateFlow<List<CategoryUiModel>> = _categories.asStateFlow()

    private val _selectedCategoryIndex = MutableStateFlow(-1)
    val selectedCategoryIndex: StateFlow<Int> = _selectedCategoryIndex.asStateFlow()

    val memes: Flow<PagingData<MimUiModel>>

    init {
        memes = selectedCategoryIndex
            .flatMapLatest { fetchMemes(it) }
            .cachedIn(viewModelScope)
    }

    fun fetchCategories(
        initSelectedCategoryId: Int
    ) {
        if (categories.value.isNotEmpty()) return
        viewModelScope.launch {
            try {
                val categories = getCategoriesUseCase().map { category ->
                    category.toUiModel()
                }
                _categories.value = categories

                val categoryIndex = categories.indexOfFirst {
                    it.id == initSelectedCategoryId
                }
                _selectedCategoryIndex.value = if (categoryIndex == -1) 0 else categoryIndex
            } catch (exception: Exception) {

            }
        }
    }

    private suspend fun fetchMemes(
        index: Int
    ): Flow<PagingData<MimUiModel>> {
        return try {
            val category = categories.value.getOrNull(index) ?: return flowOf(PagingData.empty())
            getMemesOfCategoryUseCase(categoryId = category.id)
                .map { it.map { meme -> meme.toUiModel() } }
        } catch (exception: Exception) {
            flowOf(PagingData.empty())
        }
    }

    fun changeSelectedIndex(
        index: Int
    ) {
        _selectedCategoryIndex.value = index
    }
}