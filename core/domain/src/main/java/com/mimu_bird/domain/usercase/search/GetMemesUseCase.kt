package com.mimu_bird.domain.usercase.search

import androidx.paging.PagingData
import com.mimu_bird.domain.model.meme.MemeModel
import com.mimu_bird.domain.repository.search.SearchRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 밈 검색 요청 UseCase
 */
@ViewModelScoped
class GetMemesUseCase @Inject constructor(
    private val searchRepository: SearchRepository
){
    /**
     * @param query 검색 키워드
     */
    suspend operator fun invoke(
        query: String
    ): Flow<PagingData<MemeModel>> {
        return searchRepository.getMemes(query = query)
    }
}