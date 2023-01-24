package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.SocketUpdate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

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

    fun openSocket(): Channel<SocketUpdate>

    fun closeSocket()
}