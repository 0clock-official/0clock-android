package com.xyz.oclock.core.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyInfoResponse (
    @field:Json(name = "email") val email: String,
    @field:Json(name = "nickName") val nickname: String,
    @field:Json(name = "major") val major: Int,
    @field:Json(name = "memberSex") val memberSex: Int,
    @field:Json(name = "matchingSex") val matchingSex: Int,
    @field:Json(name = "chattingTime") val chattingTime: Int,
)
