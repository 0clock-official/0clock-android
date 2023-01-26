package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.model.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    companion object {
        fun getWaitingChat(msg: String): Chat = Chat(
            message = msg,
            type = ChatType.ALERT,
            timeStamp = 0
        )
    }
    fun startMatching(
        token: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun getMatchingUserInfo(
        token: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun getMyInfo(
        token: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun getServerTime(
        token: String
    ): Flow<CommonResponse>

    fun openSocket(): Channel<SocketChatResponse>

    fun closeSocket()

    fun sendMessage(token: String, message: String, chatType: SocketChatType)
}