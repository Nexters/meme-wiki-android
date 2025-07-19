package com.mimu_bird.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SampleResponse(
    @SerialName("identifier")
    val id: String
)
