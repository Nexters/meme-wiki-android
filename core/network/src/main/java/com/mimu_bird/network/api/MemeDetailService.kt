package com.mimu_bird.network.api

import com.mimu_bird.network.model.response.common.CommonResponse
import com.mimu_bird.network.model.response.meme.MemeDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MemeDetailService {
    /**
     * 밈 상세 정보 요청
     * [GET] /api/memes/{id}
     * @param id 밈 식별자
     */
    @GET("/api/memes/{id}")
    suspend fun getMemeDetail(
        @Path("id") id: Int
    ): CommonResponse<MemeDetailResponse>
} 