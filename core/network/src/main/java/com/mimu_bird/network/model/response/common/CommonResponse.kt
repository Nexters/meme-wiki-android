package com.mimu_bird.network.model.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response 공통 객체
 */
@Serializable
data class CommonResponse<T>(
    @SerialName("resultType")
    val resultType: String,
    @SerialName("success")
    val success: T?,
    @SerialName("error")
    val error: CommonErrorResponse?
)

@Serializable
data class CommonErrorResponse (
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: String?
)
