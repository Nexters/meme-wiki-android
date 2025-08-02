package com.mimu_bird.network.util

import com.mimu_bird.common.exception.CommonErrorException
import com.mimu_bird.network.model.response.common.CommonResponse

object ApiCallUtil {
    suspend operator fun <T> invoke(
        action: suspend () -> CommonResponse<T>
    ): T {
        val response = action()
        val error = response.error

        return when (response.resultType) {
            "SUCCESS" -> response.success
            else -> throw CommonErrorException(
                code = error.code,
                message = error.message
            )
        }
    }
}