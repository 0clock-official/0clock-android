package com.xyz.oclock.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class SocketChatRequest(
    @field:Json(name = "Authorization") var authorization: String? = null,
    @field:Json(name = "message") var message: String? = null,
    @field:Json(name = "type") var type: String? = null,
)

@JsonClass(generateAdapter = true)
data class SocketChatResponse(
    @field:Json(name = "timestamp") var timestamp: String? = null,
    @field:Json(name = "message") var message: String? = null,
    @field:Json(name = "type") var type: String? = null,
)


enum class SocketChatType {
    MESSAGE, MESSAGE_OK, TIME_CHANGE_REQ, TIME_CHANGE_ACCEPT, EXIT_CHATTINGROOM, EXCEPTION
}


