package com.mimu_bird.common.exception

class CommonErrorException (
    val code: String,
    override val message: String
): Exception(message)

class NoneSuccessBodyException: Exception(
    "Success 이나 success 정보가 없습니다."
)