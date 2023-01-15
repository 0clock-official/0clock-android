package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.model.MatchingUser
import com.xyz.oclock.core.network.model.response.ChattingRoomResponse
import com.xyz.oclock.core.network.model.response.MatchingUserResponse
import com.xyz.oclock.core.network.model.response.MyInfoResponse
import com.xyz.oclock.core.network.model.response.OClockResponse
import javax.inject.Inject

class ChatClient @Inject constructor(
    private val chatService: ChatService
) {

    suspend fun startMatching(accessToken: String): ApiResponse<OClockResponse<ChattingRoomResponse>> {
        return chatService.startMatching(accessToken)
    }

    suspend fun getMatchingUserInfo(accessToken: String): ApiResponse<OClockResponse<MatchingUserResponse>> {
        return chatService.getMatchingUserInfo(accessToken)
    }

    suspend fun getMyInfo(accessToken: String): ApiResponse<OClockResponse<MyInfoResponse>> {
        return chatService.getMyInfo(accessToken)
    }

}
