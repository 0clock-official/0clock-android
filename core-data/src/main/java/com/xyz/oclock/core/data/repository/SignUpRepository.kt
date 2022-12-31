package com.xyz.oclock.core.data.repository

import android.graphics.Bitmap
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.SignUpForm
import com.xyz.oclock.core.model.Token
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    fun checkVerifyCode(
        email: String,
        code: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun sendVerifyCodeToEmail(
        email: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun checkNicknameDuplication(
        nickname: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun uploadStdCard(
        email: String,
        stdCard: Bitmap,
        accessToken: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun signUp(
        signUpForm: SignUpForm,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>

    fun checkStudentCardVerified(
        accessToken: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<CommonResponse>
}