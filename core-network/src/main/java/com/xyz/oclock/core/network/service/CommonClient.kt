package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.request.LoginRequest
import com.xyz.oclock.core.network.model.response.OClockResponse
import com.xyz.oclock.core.network.model.response.TokenResponse
import javax.inject.Inject

class CommonClient @Inject constructor(
    private val commonService: CommonService
) {
    suspend fun getNewToken(refreshToken: String): ApiResponse<OClockResponse<Any>> {
        return commonService.getNewToken(refreshToken)
    }

    suspend fun login(email: String, password: String): ApiResponse<OClockResponse<TokenResponse>> {
        return commonService.login(LoginRequest(email, password))
    }
}