package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.request.EmailVerificationRequest
import com.xyz.oclock.core.network.model.response.EmailVerificationResponse
import javax.inject.Inject

class SignUpClient @Inject constructor(
    private val signUpService: SignUpService
) {

    suspend fun checkVerifyCode(email: String, code: String): ApiResponse<EmailVerificationResponse> {
        return signUpService.checkVerifyCode(EmailVerificationRequest(email, code))
    }


}