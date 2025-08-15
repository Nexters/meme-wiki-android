package com.mimu_bird.network.model.response.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryNetworkModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("imgUrl") val imageUrl: String
)