package com.mimu_bird.network.model.response.meme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Top Rated 밈 Response
 */
@Serializable
data class TopRatedMemeResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("imgUrl")
    val imgUrl: String
) 