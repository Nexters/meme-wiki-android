package com.mimu_bird.data.repository.meme

import com.mimu_bird.domain.model.category.BriefMemeModel
import com.mimu_bird.domain.model.meme.SharedMemeModel
import com.mimu_bird.domain.repository.meme.SharedMemeRepository
import com.mimu_bird.network.api.SharedMemeService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 공유된 밈 순위 관련 로직 관리 Repository 구현체
 */
@Singleton
class SharedMemeRepositoryImpl @Inject constructor(
    private val sharedMemeService: SharedMemeService
) : SharedMemeRepository {

    override suspend fun getSharedMemes(): SharedMemeModel {
        return try {
            val response = sharedMemeService.getSharedMemes()
            response.success?.let { sharedMemeResponse ->
                val memes = sharedMemeResponse.memes.map { values ->
                    BriefMemeModel(
                        id = values.id,
                        name = values.title,
                        imageUrl = values.imgUrl
                    )
                }
                SharedMemeModel(
                    memes = memes,
                    nextFetchTime = sharedMemeResponse.nextFetchTime
                )
            } ?: SharedMemeModel(
                memes = emptyList(),
                nextFetchTime = "2025-08-17T04:00:00"
            )
        } catch (e: Exception) {
            SharedMemeModel(
                memes = emptyList(),
                nextFetchTime = "2025-08-17T04:00:00"
            )
        }
    }
} 