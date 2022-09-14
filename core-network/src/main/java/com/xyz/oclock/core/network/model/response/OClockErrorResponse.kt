package com.xyz.oclock.core.network.model.response

data class OClockErrorResponse(
    val code: Int,
    val response: String = ""
)