package com.meme.search.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mimu_bird.domain.usercase.search.GetMemesUseCase
import com.mimu_bird.ui.model.MimUiModel
import com.mimu_bird.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MemeSearchViewModel @Inject constructor(
    private val getMemesUseCase: GetMemesUseCase
) : ViewModel() {
    /**
     * 검색 keyword
     */
    private val _keyword = MutableStateFlow("")
    val keyword: StateFlow<String> = _keyword.asStateFlow()

    val memes: Flow<PagingData<MimUiModel>>

    init {
        memes = keyword
            .flatMapLatest { fetchMemes(query = it) }
            .cachedIn(viewModelScope)
    }

    private suspend fun fetchMemes(
        query: String
    ): Flow<PagingData<MimUiModel>> {
        return try {
            getMemesUseCase(query = query)
                .map { it.map { meme -> meme.toUiModel() } }
        } catch (exception: Exception) {
            flowOf(PagingData.empty())
        }
    }

    fun changeKeyword(
        keyword: String
    ) {
        _keyword.value = keyword
    }
}