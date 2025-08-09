package com.mimu_bird.main.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimu_bird.domain.model.category.CategoryModel
import com.mimu_bird.domain.usercase.category.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories: StateFlow<List<CategoryModel>> = _categories.asStateFlow()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            runCatching {
                getCategoriesUseCase()
            }.onSuccess { list ->
                _categories.value = list
            }.onFailure {
                _categories.value = emptyList()
            }
        }
    }
}