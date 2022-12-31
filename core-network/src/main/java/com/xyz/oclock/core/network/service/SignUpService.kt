package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.request.*
import com.xyz.oclock.core.network.model.response.OClockResponse
import com.xyz.oclock.core.network.model.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignUpService {

    @POST("members/email/verification")
    suspend fun checkVerifyCode(@Body body: EmailVerificationRequest): ApiResponse<OClockResponse<Any>>

    @POST("members/email")
    suspend fun sendVerifyCodeToEmail(@Body body: EmailRequest): ApiResponse<OClockResponse<Any>>

    @POST("members/정해야함")
    suspend fun checkNicknameDuplication(@Body body: NicknameRequest): ApiResponse<OClockResponse<Any>>

    @POST("members/join/studentCard")
    suspend fun uploadStdCard(
        @Header("Authorization") token: String,
        @Body body: StdCardUploadRequest
    ): ApiResponse<OClockResponse<Any>>

    @POST("members/join")
    suspend fun signUp(@Body body: SignUpRequest): ApiResponse<OClockResponse<SignUpResponse>>

    @POST("members/join/studentCard/cert")
    suspend fun checkStudentCardVerified(@Header("Authorization") token: String): ApiResponse<OClockResponse<Boolean>>

}