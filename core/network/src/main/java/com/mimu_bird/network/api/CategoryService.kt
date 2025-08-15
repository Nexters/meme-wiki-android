package com.mimu_bird.network.api

import com.mimu_bird.network.model.response.common.CommonResponse
import com.mimu_bird.network.model.response.search.MemeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryService {
    /**
     * 카테고리 목록 요청
     * [GET] /api/memes/categories
     */
    @GET("/api/memes/categories")
    suspend fun getCategories(): CommonResponse<List<CategoryNetworkModel>>

    /**
     * 특정 카테고리의 밈 정보 요청
     * [GET] /memes/categories/{category_uuid}
     * @param categoryId 요청하는 카테고리 식별자
     * @param next 검색 cursor
     * @param limit 페이지 당 결과 개수
     */
    @GET("/api/memes/categories/{category_uuid}")
    suspend fun getMemesOfCategory(
        @Path("category_uuid") categoryId: String,
        @Query("next") next: Int?,
        @Query("limit") limit: Int
    ): CommonResponse<MemeSearchResponse>
}