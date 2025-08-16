package com.mimu_bird.data.repository.meme

import com.mimu_bird.domain.model.meme.SharedMemeModel
import com.mimu_bird.domain.repository.meme.SharedMemeRepository
import com.mimu_bird.network.api.SharedMemeService
import com.mimu_bird.network.util.ApiCallUtil
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 공유된 밈 순위 관련 로직 관리 Repository 구현체
 */
@Singleton
class SharedMemeRepositoryImpl @Inject constructor(
    private val sharedMemeService: SharedMemeService
) : SharedMemeRepository {

    override suspend fun getSharedMemes(): List<SharedMemeModel> {
        return try {
            val response = ApiCallUtil {
                sharedMemeService.getSharedMemes()
            }
            response.memes.map { memeResponse ->
                SharedMemeModel(
                    id = memeResponse.id,
                    title = memeResponse.title,
                    imageUrl = memeResponse.imgUrl,
                    nextFetchTime = response.nextFetchTime
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
} 