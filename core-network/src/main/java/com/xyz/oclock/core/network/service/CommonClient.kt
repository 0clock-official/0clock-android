package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.core.model.Sex
import com.xyz.oclock.core.network.model.request.EditProfileRequest
import com.xyz.oclock.core.network.model.request.LoginRequest
import com.xyz.oclock.core.network.model.response.OClockResponse
import com.xyz.oclock.core.network.model.response.TokenResponse
import javax.inject.Inject

class CommonClient @Inject constructor(
    private val commonService: CommonService
) {

    suspend fun login(email: String, password: String): ApiResponse<OClockResponse<TokenResponse>> {
        return commonService.login(LoginRequest(email, password))
    }

    suspend fun editProfile(token:String, nickname: String, chattingTime: ChattingTime, matchingSex: Sex)
            : ApiResponse<OClockResponse<Any>> {
        val request = EditProfileRequest(
            nickname = nickname,
            chattingTime = chattingTime.index.toString(),
            matchingSex = matchingSex.index.toString()
        )
        return commonService.editProfile(token, request)
    }
}