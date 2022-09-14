package com.xyz.oclock.core.network.model.response

data class OClockResponse<T>(
    val code: Int,
    val response: String,
    val data: T? = null
)
