package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.response.ChattingRoomResponse
import com.xyz.oclock.core.network.model.response.MatchingUserResponse
import com.xyz.oclock.core.network.model.response.MyInfoResponse
import com.xyz.oclock.core.network.model.response.OClockResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatService {

    @POST("chatting/room/rand")
    suspend fun startMatching(@Header("Authorization") token: String): ApiResponse<OClockResponse<ChattingRoomResponse>>

    @GET("members/other")
    suspend fun getMatchingUserInfo(@Header("Authorization") token: String): ApiResponse<OClockResponse<MatchingUserResponse>>

    @GET("members/self")
    suspend fun getMyInfo(@Header("Authorization") token: String): ApiResponse<OClockResponse<MyInfoResponse>>

}