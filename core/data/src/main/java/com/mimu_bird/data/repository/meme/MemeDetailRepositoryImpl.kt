package com.mimu_bird.data.repository.meme

import com.mimu_bird.domain.model.meme.MemeDetailModel
import com.mimu_bird.domain.repository.meme.MemeDetailRepository
import com.mimu_bird.data.datasource.meme.MemeDetailDataSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 밈 상세 정보 관련 로직 관리 Repository 구현체
 */
@Singleton
class MemeDetailRepositoryImpl @Inject constructor(
    private val memeDetailDataSource: MemeDetailDataSource
) : MemeDetailRepository {

    override suspend fun getMemeDetail(id: Int): MemeDetailModel {
        return try {
            val response = memeDetailDataSource.getMemeDetail(id)
            MemeDetailModel(
                id = response.id,
                title = response.title,
                usageContext = response.usageContext,
                origin = response.origin,
                trendPeriod = response.trendPeriod,
                imgUrl = response.imgUrl,
                hashtags = response.hashtags
            )
        } catch (e: Exception) {
            // 에러 발생 시 기본값 반환
            MemeDetailModel(
                id = id,
                title = "",
                usageContext = "",
                origin = "",
                trendPeriod = "",
                imgUrl = "",
                hashtags = emptyList()
            )
        }
    }
} 