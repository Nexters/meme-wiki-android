package com.mimu_bird.data.datasource.meme

import com.mimu_bird.network.api.MemeDetailService
import com.mimu_bird.network.model.response.meme.MemeDetailResponse
import com.mimu_bird.network.util.ApiCallUtil
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 밈 상세 정보 관련 로직 관리 Data Source
 */
@Singleton
class MemeDetailDataSource @Inject constructor(
    private val memeDetailService: MemeDetailService
) {
    /**
     * 밈 상세 정보 요청
     * @param id 밈 식별자
     */
    suspend fun getMemeDetail(id: Int): MemeDetailResponse {
        return ApiCallUtil {
            memeDetailService.getMemeDetail(id)
        }
    }
} 