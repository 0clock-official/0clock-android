package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.model.CommonResponse
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
}