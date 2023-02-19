package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.Sex
import kotlinx.coroutines.flow.Flow

interface CommonRepository {
    fun login(
        email: String,
        password: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun editProfile(
        token: String,
        nickname: String,
        chattingTime: ChattingTime,
        matchingSex: Sex,
        onError: (String?) -> Unit,
        onStart: () -> Unit,
        onComplete: () -> Unit,
    ): Flow<CommonResponse>
}