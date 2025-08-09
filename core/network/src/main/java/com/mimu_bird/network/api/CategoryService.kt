package com.mimu_bird.network.api

import com.mimu_bird.network.model.response.common.CommonResponse
import com.mimu_bird.network.model.response.category.CategoryNetworkModel
import retrofit2.http.GET

interface CategoryService {
    /**
     * 카테고리 목록 요청
     * [GET] /api/memes/categories
     */
    @GET("/api/memes/categories")
    suspend fun getCategories(): CommonResponse<List<CategoryNetworkModel>>
}