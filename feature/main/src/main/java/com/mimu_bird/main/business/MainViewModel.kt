package com.mimu_bird.main.business

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimu_bird.domain.model.category.BriefMemeModel
import com.mimu_bird.domain.model.meme.TopRatedMemeModel
import com.mimu_bird.domain.usercase.category.GetCategoriesUseCase
import com.mimu_bird.domain.usercase.meme.GetSharedMemesUseCase
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
    private val getTopRatedMemesUseCase: GetTopRatedMemesUseCase,
    private val getSharedMemesUseCase: GetSharedMemesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<BriefMemeModel>>(emptyList())
    val categories: StateFlow<List<BriefMemeModel>> = _categories.asStateFlow()

    private val _topRatedMemes = MutableStateFlow<List<TopRatedMemeModel>>(emptyList())
    val topRatedMemes: StateFlow<List<TopRatedMemeModel>> = _topRatedMemes.asStateFlow()

    private val _sharedMemes = MutableStateFlow<List<BriefMemeModel>>(emptyList())
    val sharedMemes: StateFlow<List<BriefMemeModel>> = _sharedMemes.asStateFlow()

    private val _nextFetchTime = MutableStateFlow<String>("")
    val nextFetchTime: StateFlow<String> = _nextFetchTime.asStateFlow()

    init {
        fetchCategories()
        fetchTopRatedMemes()
        fetchSharedMemes()
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

    private fun fetchSharedMemes() {
        viewModelScope.launch {
            runCatching {
                getSharedMemesUseCase()
            }.onSuccess { sharedMemeModel ->
                Log.d("MainViewModel", "fetchSharedMemes: API 호출 성공")
                Log.d(
                    "MainViewModel",
                    "fetchSharedMemes: memes: ${sharedMemeModel.memes}, nextFetchTime: ${sharedMemeModel.nextFetchTime}"
                )
                _nextFetchTime.value = sharedMemeModel.nextFetchTime
                _sharedMemes.value = sharedMemeModel.memes
            }.onFailure { exception ->
                Log.e("MainViewModel", "fetchSharedMemes: API 호출 실패, dummy data 사용", exception)
                // API 호출 실패 시 dummy data 사용
                _sharedMemes.value = emptyList()
                _nextFetchTime.value = "2025-08-25T04:00:00"
            }
        }
    }

}