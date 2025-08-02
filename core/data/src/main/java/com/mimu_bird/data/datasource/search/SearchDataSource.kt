package com.mimu_bird.data.datasource.search

import com.mimu_bird.network.api.SearchService
import com.mimu_bird.network.model.response.search.MemeSearchResponse
import com.mimu_bird.network.util.ApiCallUtil
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 검색 관련 로직 관리 Data Source
 */
@Singleton
class SearchDataSource @Inject constructor(
    private val searchService: SearchService
){
    /**
     * 검색 결과 요청
     * @param next cursor
     * @param query 검색 키워드
     * @param limit 페이지 당 결과 개수
     */
    suspend fun getMemes(
        next: Int?,
        query: String,
        limit: Int
    ): MemeSearchResponse {
        return ApiCallUtil {
            searchService.getMemes(
                next = next,
                query = query,
                limit = limit
            )
        }
    }
}