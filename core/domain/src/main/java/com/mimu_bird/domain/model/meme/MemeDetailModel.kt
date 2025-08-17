package com.mimu_bird.domain.model.meme

/**
 * 밈 상세 정보 도메인 모델
 */
data class MemeDetailModel(
    val id: Int,
    val title: String,
    val usageContext: String,
    val origin: String,
    val trendPeriod: String,
    val imgUrl: String,
    val hashtags: List<String>
) 