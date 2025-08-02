package com.mimu_bird.network.model.response.search

import com.mimu_bird.network.model.response.meme.MemeResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 밈 검색 결과 Response
 */
@Serializable
data class MemeSearchResponse(
    @SerialName("paging")
    val page: MemeSearchPagingBody,
    @SerialName("results")
    val memes: List<MemeResponse>
)

/**
 * 밈 검색 결과 페이징 정보
 */
@Serializable
data class MemeSearchPagingBody(
    @SerialName("next")
    val next: Int,
    @SerialName("hasMore")
    val hasMore: Boolean,
    @SerialName("pageSize")
    val pageSize: Int
)