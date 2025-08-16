package com.mimu_bird.main.business

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimu_bird.domain.model.category.BriefMemeModel
import com.mimu_bird.domain.model.meme.SharedMemeModel
import com.mimu_bird.domain.model.meme.TopRatedMemeModel
import com.mimu_bird.domain.usercase.category.GetCategoriesUseCase
import com.mimu_bird.domain.usercase.meme.GetSharedMemesUseCase
import com.mimu_bird.domain.usercase.meme.GetTopRatedMemesUseCase
import com.mimu_bird.ui.model.TEST_BRIEF_MEME_UI
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

    private val _sharedMemes = MutableStateFlow<List<SharedMemeModel>>(emptyList())
    val sharedMemes: StateFlow<List<SharedMemeModel>> = _sharedMemes.asStateFlow()

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
            }.onSuccess { list ->
                _sharedMemes.value = list
                if (list.isNotEmpty()) {
                    val nextFetchTime = list.first().nextFetchTime
                    Log.d("MainViewModel", "fetchSharedMemes: nextFetchTime 설정: $nextFetchTime")
                    _nextFetchTime.value = nextFetchTime
                } else {
                    Log.w("MainViewModel", "fetchSharedMemes: 받은 밈 리스트가 비어있음")
                    val dummyData = createDummySharedMemes()
                    _sharedMemes.value = dummyData
                    _nextFetchTime.value = "2025-08-17T04:00:00"
                }
            }.onFailure { exception ->
                Log.w("MainViewModel", "fetchSharedMemes: API 호출 실패, dummy data 사용", exception)
                // API 호출 실패 시 dummy data 사용
                val dummyData = createDummySharedMemes()
                _sharedMemes.value = dummyData
                _nextFetchTime.value = "2025-08-25T04:00:00"
            }
        }
    }

    /**
     * API 호출 실패 시 사용할 dummy data 생성
     */
    private fun createDummySharedMemes(): List<SharedMemeModel> {
        return listOf(
            SharedMemeModel(
                id = 63,
                title = "오히려 좋아",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 62,
                title = "손민수",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 61,
                title = "너 T야?",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 60,
                title = "반박 시 님 말이 맞음",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 59,
                title = "누가 칼들고 협박함?",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 58,
                title = "미안하다 이거 보여주려고 어그로끌었다.",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 57,
                title = "무대를 뒤집어 놓으셨다",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 56,
                title = "나만 아니면 돼",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 55,
                title = "호의가 계속되면은, 그게 권리인 줄 알아요",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            ),
            SharedMemeModel(
                id = 54,
                title = "어쩔티비",
                imageUrl = TEST_BRIEF_MEME_UI.imageUrl,
                nextFetchTime = "2025-08-17T04:00:00"
            )
        )
    }
}