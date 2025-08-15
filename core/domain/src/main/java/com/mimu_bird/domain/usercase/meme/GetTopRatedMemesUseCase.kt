package com.mimu_bird.domain.usercase.meme

import com.mimu_bird.domain.model.meme.TopRatedMemeModel
import com.mimu_bird.domain.repository.meme.TopRatedMemeRepository
import javax.inject.Inject

class GetTopRatedMemesUseCase @Inject constructor(
    private val topRatedMemeRepository: TopRatedMemeRepository
) {
    suspend operator fun invoke(): List<TopRatedMemeModel> {
        return topRatedMemeRepository.getTopRatedMemes()
    }
} 