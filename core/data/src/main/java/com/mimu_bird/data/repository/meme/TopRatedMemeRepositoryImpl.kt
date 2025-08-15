package com.mimu_bird.data.repository.meme

import com.mimu_bird.domain.model.meme.TopRatedMemeModel
import com.mimu_bird.domain.repository.meme.TopRatedMemeRepository
import com.mimu_bird.network.api.TopRatedMemeService
import javax.inject.Inject

class TopRatedMemeRepositoryImpl @Inject constructor(
    private val topRatedMemeService: TopRatedMemeService
) : TopRatedMemeRepository {

    override suspend fun getTopRatedMemes(): List<TopRatedMemeModel> {
        return try {
            val response = topRatedMemeService.getTopRatedMemes()
            response.success?.map { memeResponse ->
                TopRatedMemeModel(
                    id = memeResponse.id,
                    title = memeResponse.title,
                    imageUrl = memeResponse.imgUrl
                )
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
} 