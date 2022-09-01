package com.xyz.oclock.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpStepResponse(
    @field:Json(name = "step") val step: Int
)
