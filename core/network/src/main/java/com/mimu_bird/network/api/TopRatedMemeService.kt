package com.mimu_bird.network.api

import com.mimu_bird.network.model.response.common.CommonResponse
import com.mimu_bird.network.model.response.meme.TopRatedMemeResponse
import retrofit2.http.GET

interface TopRatedMemeService {
    /**
     * Top Rated 밈 목록 요청
     * [GET] /api/memes/rankings/top-rated
     */
    @GET("/api/memes/rankings/top-rated")
    suspend fun getTopRatedMemes(): CommonResponse<List<TopRatedMemeResponse>>
} 