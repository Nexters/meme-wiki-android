package com.mimu_bird.data.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mimu_bird.data.datasource.search.SearchDataSource
import com.mimu_bird.data.util.toModel
import com.mimu_bird.domain.model.meme.MemeModel
import com.mimu_bird.domain.repository.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 검색 관련 로직 관리 Repository
 */
class DefaultSearchRepository @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository {

    // 밈 검색 요청
    override suspend fun getMemes(
        query: String
    ): Flow<PagingData<MemeModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = MemePagingSource.LIMIT_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MemePagingSource(
                    query = query,
                    getMemes = { next, query, limit ->
                        searchDataSource.getMemes(
                            next = next,
                            query = query,
                            limit = limit
                        )
                    }
                )
            }
        ).flow.map {
            it.map { meme -> meme.toModel() }
        }
    }
}