package com.xyz.oclock.core.model

data class Chat (
    val message: String,
    val type: ChatType,
    val timeStamp: Long
)

enum class ChatType {
    CHAT_ME, CHAT_YOU, ALERT, SELECT, NOTHING, EXCEPTION,
}