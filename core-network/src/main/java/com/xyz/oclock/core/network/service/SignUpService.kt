package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.request.EmailRequest
import com.xyz.oclock.core.network.model.request.EmailVerificationRequest
import com.xyz.oclock.core.network.model.request.NicknameRequest
import com.xyz.oclock.core.network.model.response.EmailVerificationResponse
import com.xyz.oclock.core.network.model.response.OClockResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignUpService {

    @POST("members/email/verification")
    suspend fun checkVerifyCode(@Body body: EmailVerificationRequest): ApiResponse<EmailVerificationResponse>

    @POST("members/email")
    suspend fun sendVerifyCodeToEmail(@Body body: EmailRequest): ApiResponse<OClockResponse>

    @POST("members/정해야함")
    suspend fun checkNicknameDuplication(@Body body: NicknameRequest): ApiResponse<OClockResponse>

}