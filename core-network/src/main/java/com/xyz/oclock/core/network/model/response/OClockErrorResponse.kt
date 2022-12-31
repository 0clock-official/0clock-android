package com.xyz.oclock.core.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OClockErrorResponse(
    @field:Json(name = "requestId") val requestId: String?,
    @field:Json(name = "code") val code: Int,
    @field:Json(name = "message") val response: String
)