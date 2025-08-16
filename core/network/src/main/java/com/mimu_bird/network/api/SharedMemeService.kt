package com.mimu_bird.network.api

import com.mimu_bird.network.model.response.common.CommonResponse
import com.mimu_bird.network.model.response.meme.SharedMemeResponse
import retrofit2.http.GET

/**
 * 공유된 밈 순위 API 서비스
 */
interface SharedMemeService {
    /**
     * 공유된 밈 순위 조회
     */
    @GET("/api/memes/rankings/shared")
    suspend fun getSharedMemes(): CommonResponse<SharedMemeResponse>
} 