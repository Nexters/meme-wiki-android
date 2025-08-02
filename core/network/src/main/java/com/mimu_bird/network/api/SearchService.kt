package com.mimu_bird.network.api

import com.mimu_bird.network.model.response.common.CommonResponse
import com.mimu_bird.network.model.response.search.MemeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    /**
     * 밈 검색 요청
     * [GET] /memes
     * @param next 검색 cursor
     * @param query 검색 query
     * @param limit 페이지 당 결과 개수
     */
    @GET("/memes")
    suspend fun getMemes(
        @Query("next") next: Int?,
        @Query("query") query: String,
        @Query("limit") limit: Int
    ): CommonResponse<MemeSearchResponse>
}