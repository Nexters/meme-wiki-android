package com.mimu_bird.domain.repository.meme

import com.mimu_bird.domain.model.meme.SharedMemeModel

/**
 * 공유된 밈 순위 관련 로직 관리 Repository
 */
interface SharedMemeRepository {
    /**
     * 공유된 밈 순위 조회
     */
    suspend fun getSharedMemes(): SharedMemeModel
} 