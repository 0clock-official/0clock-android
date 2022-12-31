package com.xyz.oclock.core.network.model.response

data class OClockResponse<T: Any>(
    val code: String,
    val response: String,
    val data: T? = null
)
