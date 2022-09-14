package com.xyz.oclock.core.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmailVerificationResponse(
    @field:Json(name = "token") val token: String
)
