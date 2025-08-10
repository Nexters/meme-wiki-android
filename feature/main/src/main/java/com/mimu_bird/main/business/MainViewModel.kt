package com.mimu_bird.main.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimu_bird.domain.model.category.BriefMemeModel
import com.mimu_bird.domain.model.meme.TopRatedMemeModel
import com.mimu_bird.domain.usercase.category.GetCategoriesUseCase
import com.mimu_bird.domain.usercase.meme.GetTopRatedMemesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getTopRatedMemesUseCase: GetTopRatedMemesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<BriefMemeModel>>(emptyList())
    val categories: StateFlow<List<BriefMemeModel>> = _categories.asStateFlow()

    private val _topRatedMemes = MutableStateFlow<List<TopRatedMemeModel>>(emptyList())
    val topRatedMemes: StateFlow<List<TopRatedMemeModel>> = _topRatedMemes.asStateFlow()

    init {
        fetchCategories()
        fetchTopRatedMemes()
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

    private fun fetchTopRatedMemes() {
        viewModelScope.launch {
            runCatching {
                getTopRatedMemesUseCase()
            }.onSuccess { list ->
                _topRatedMemes.value = list
            }.onFailure {
                _topRatedMemes.value = emptyList()
            }
        }
    }
}