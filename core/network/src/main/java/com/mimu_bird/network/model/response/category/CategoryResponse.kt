package com.mimu_bird.network.model.response.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("id")
    val categoryId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("imgUrl")
    val imageUrl: String
)