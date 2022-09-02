package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.model.LoginStep

interface SignUpRepository {

    suspend fun checkEnabledEmail(email: String, onError: (String?)->Unit): LoginStep

    suspend fun checkVerifyCode(email: String, verifyCode: String): Boolean
}