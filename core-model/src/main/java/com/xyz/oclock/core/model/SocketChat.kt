package com.xyz.oclock.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SocketChat(
    @field:Json(name = "Authorization") var authorization: String? = null,
    @field:Json(name = "message") var message: String? = null,
    @field:Json(name = "type") var type: String? = null,
)

enum class SocketChatType {
    MESSAGE, TIME_CHANGE_REQ, TIME_CHANGE_ACCEPT, EXIT_CHATTINGROOM, EXEPTION
}
