package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.model.Token
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    fun checkVerifyCode(
        email: String,
        code: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Token>

    fun sendVerifyCodeToEmail(
        email: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Boolean>
}