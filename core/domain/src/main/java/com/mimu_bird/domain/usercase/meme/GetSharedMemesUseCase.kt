package com.mimu_bird.domain.usercase.meme

import com.mimu_bird.domain.model.meme.SharedMemeModel
import com.mimu_bird.domain.repository.meme.SharedMemeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * 공유된 밈 순위 조회 UseCase
 */
@ViewModelScoped
class GetSharedMemesUseCase @Inject constructor(
    private val sharedMemeRepository: SharedMemeRepository
) {
    suspend operator fun invoke(): List<SharedMemeModel> {
        return sharedMemeRepository.getSharedMemes()
    }
} 