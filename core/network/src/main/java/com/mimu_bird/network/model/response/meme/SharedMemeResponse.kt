package com.mimu_bird.network.model.response.meme

import com.google.gson.annotations.SerializedName

/**
 * 공유된 밈 순위 API 응답 모델
 */
data class SharedMemeResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("imgUrl")
    val imgUrl: String
)

/**
 * 공유된 밈 순위 API 전체 응답 모델
 */
data class SharedMemeListResponse(
    @SerializedName("memes")
    val memes: List<SharedMemeResponse>,
    @SerializedName("nextFetchTime")
    val nextFetchTime: String
) 