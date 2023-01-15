package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.request.LoginRequest
import com.xyz.oclock.core.network.model.response.OClockResponse
import com.xyz.oclock.core.network.model.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CommonService {

    @POST("auth/accessToken")
    suspend fun getNewToken(@Header("Authorization") token: String): ApiResponse<OClockResponse<Any>>

    @POST("members/login")
    suspend fun login(@Body body: LoginRequest): ApiResponse<OClockResponse<TokenResponse>>
}