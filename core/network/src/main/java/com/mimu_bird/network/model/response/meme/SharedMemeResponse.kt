package com.mimu_bird.network.model.response.meme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 공유된 밈 순위 API 응답 모델
 */
@Serializable
data class SharedMemeResponse(
    @SerialName("memes")
    val memes: List<TopRatedMemeResponse>,
    @SerialName("nextFetchTime")
    val nextFetchTime: String
)

