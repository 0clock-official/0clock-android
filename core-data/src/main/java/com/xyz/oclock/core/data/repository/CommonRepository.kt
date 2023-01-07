package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.model.CommonResponse
import kotlinx.coroutines.flow.Flow

interface CommonRepository {

    fun getNewToken(
        refreshToken: String,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun login(
        email: String,
        password: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>
}