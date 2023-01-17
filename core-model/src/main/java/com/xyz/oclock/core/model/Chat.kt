package com.xyz.oclock.core.model

data class Chat (
    val message: String,
    val type: ChatType,
    val timeStamp: String
)

enum class ChatType {
    CHAT_U1, CHAT_U2, ALERT, SELECT
}