package com.mimu_bird.domain.usercase.meme

import com.mimu_bird.domain.model.meme.MemeDetailModel
import com.mimu_bird.domain.repository.meme.MemeDetailRepository
import javax.inject.Inject

/**
 * 밈 상세 정보 조회 UseCase
 */
class GetMemeDetailUseCase @Inject constructor(
    private val memeDetailRepository: MemeDetailRepository
) {
    /**
     * 밈 상세 정보 조회
     * @param id 밈 식별자
     */
    suspend operator fun invoke(id: Int): Result<MemeDetailModel> {
        return kotlin.runCatching {
            memeDetailRepository.getMemeDetail(id)
        }
    }
} 