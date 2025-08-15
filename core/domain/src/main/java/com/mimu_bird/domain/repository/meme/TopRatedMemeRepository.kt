package com.mimu_bird.domain.repository.meme

import com.mimu_bird.domain.model.meme.TopRatedMemeModel

interface TopRatedMemeRepository {
    suspend fun getTopRatedMemes(): List<TopRatedMemeModel>
} 