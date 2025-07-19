package com.mimu_bird.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SampleRequest(
    @SerialName("identifier")
    val id: String
)
