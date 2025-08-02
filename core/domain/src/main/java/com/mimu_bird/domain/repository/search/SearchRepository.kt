package com.mimu_bird.domain.repository.search

import androidx.paging.PagingData
import com.mimu_bird.domain.model.meme.MemeModel
import kotlinx.coroutines.flow.Flow

/**
 * 검색 관련 로직 관리 Repository
 */
interface SearchRepository {
    /**
     * 밈 검색 요청
     * @param query 검색 키워드
     */
    suspend fun getMemes(
        query: String
    ): Flow<PagingData<MemeModel>>
}