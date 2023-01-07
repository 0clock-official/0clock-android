package com.xyz.oclock.core.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchingUserResponse (
    @field:Json(name = "nickname") val nickname: String,
    @field:Json(name = "major") val major: Int,
    @field:Json(name = "sex") val sex: Int,
)