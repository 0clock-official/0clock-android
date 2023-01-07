package com.xyz.oclock.core.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChattingRoomResponse (
    @field:Json(name = "chattingRoomId") val chattingRoomId: Int
)