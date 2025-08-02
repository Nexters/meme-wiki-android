package com.mimu_bird.common.exception

class CommonErrorException (
    val code: String,
    override val message: String
): Exception(
    message = message
)