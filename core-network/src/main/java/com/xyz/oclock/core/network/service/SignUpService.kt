package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.request.EmailVerificationRequest
import com.xyz.oclock.core.network.model.response.EmailVerificationResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface SignUpService {

    @GET("members/email/verification")
    suspend fun checkVerifyCode(@Body body: EmailVerificationRequest): ApiResponse<EmailVerificationResponse>

}