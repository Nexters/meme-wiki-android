package com.mimu_bird.domain.repository.meme

import com.mimu_bird.domain.model.meme.MemeDetailModel

/**
 * 밈 상세 정보 Repository 인터페이스
 */
interface MemeDetailRepository {
    /**
     * 밈 상세 정보 조회
     * @param id 밈 식별자
     */
    suspend fun getMemeDetail(id: Int): MemeDetailModel
} 