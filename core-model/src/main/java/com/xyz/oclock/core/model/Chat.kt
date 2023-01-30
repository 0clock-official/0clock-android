package com.xyz.oclock.core.model

data class Chat (
    val message: String,
    val type: ChatType,
    val timeStamp: Long
)

enum class ChatType(val viewType: Int) {
    CHAT_ME(0), CHAT_YOU(1), ALERT(2),
    SELECT(3), NOTHING(4), EXCEPTION(5),
}