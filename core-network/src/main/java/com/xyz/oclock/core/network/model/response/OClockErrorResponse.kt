package com.xyz.oclock.core.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OClockErrorResponse(
     val requestId: String?,
     val code: Int,
     val message: String
)