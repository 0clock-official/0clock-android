package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.request.EmailRequest
import com.xyz.oclock.core.network.model.request.EmailVerificationRequest
import com.xyz.oclock.core.network.model.request.NicknameRequest
import com.xyz.oclock.core.network.model.response.EmailVerificationResponse
import com.xyz.oclock.core.network.model.response.OClockResponse
import javax.inject.Inject

class SignUpClient @Inject constructor(
    private val signUpService: SignUpService
) {

    suspend fun checkVerifyCode(email: String, code: String): ApiResponse<EmailVerificationResponse> {
        return signUpService.checkVerifyCode(EmailVerificationRequest(email, code))
    }

    suspend fun sendVerifyCodeToEmail(email: String): ApiResponse<OClockResponse> {
        return signUpService.sendVerifyCodeToEmail(EmailRequest(email))
    }

    suspend fun checkNicknameDuplication(nickname: String): ApiResponse<OClockResponse> {
        return signUpService.checkNicknameDuplication(NicknameRequest(nickname))
    }
}