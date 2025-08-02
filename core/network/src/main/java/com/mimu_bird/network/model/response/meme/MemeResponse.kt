package com.mimu_bird.network.model.response.meme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 검색 결과 Response
 */
@Serializable
data class MemeResponse(
    @SerialName("id")
    val id: Int, // 밈 식별자
    @SerialName("title")
    val title: String, // 밈 제목
    @SerialName("usageContext")
    val usage: String, // 밈 용도 정보
    @SerialName("origin")
    val origin: String, // 밈 유래 정보
    @SerialName("trendPeriod")
    val trendPeriod: String, // 유행한 연도
    @SerialName("imgUrl")
    val imageUrl: String, // 이미지 Url
    @SerialName("hashtags")
    val hashTags: List<String>
)

