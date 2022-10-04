package com.xyz.oclock.core.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OClockErrorResponse(
    @field:Json(name = "code") val code: String,
    @field:Json(name = "response") val response: String
)