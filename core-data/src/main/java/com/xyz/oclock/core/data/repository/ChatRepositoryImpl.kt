package com.xyz.oclock.core.data.repository

import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.MatchingUser
import com.xyz.oclock.core.model.User
import com.xyz.oclock.core.network.model.mapper.ErrorResponseMapper
import com.xyz.oclock.core.network.service.ChatClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatClient: ChatClient
): ChatRepository {

    override fun startMatching(
        token: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = chatClient.startMatching(token)
        response.suspendOnSuccess {
            val roomId = this.data.data?.chattingRoomId
            if (roomId == null) {
                onError("room id is null")
            } else {
                emit(CommonResponse.Success(this.data.response, this.data.data!!.chattingRoomId))
            }
        }.suspendOnError {
            val errorResponse = ErrorResponseMapper.map(this)
            emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
        }.onException {
            onError(this.message)
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(Dispatchers.IO)

    override fun getMatchingUserInfo(
        token: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = chatClient.getMatchingUserInfo(token)
        response.suspendOnSuccess {
            val data = this.data.data
            if (data == null) {
                onError("matching user info is null")
            } else {
                val matchingUser = MatchingUser(
                    nickname = data.nickname,
                    major = data.major,
                    sex = data.sex
                )
                emit(CommonResponse.Success(this.data.response, matchingUser))
            }
        }.suspendOnError {
            val errorResponse = ErrorResponseMapper.map(this)
            emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
        }.onException {
            onError(this.message)
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(Dispatchers.IO)

    override fun  getMyInfo(
        token: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = chatClient.getMyInfo(token)
        response.suspendOnSuccess {
            val data = this.data.data
            if (data == null) {
                onError("user info is null")
            } else {
                val user = User(
                    email = data.email,
                    nickname = data.nickname,
                    major = data.major,
                    memberSex = data.memberSex,
                    matchingSex = data.matchingSex,
                    chattingTime = data.chattingTime
                )
                emit(CommonResponse.Success(this.data.response, user))
            }
        }.suspendOnError {
            val errorResponse = ErrorResponseMapper.map(this)
            emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
        }.onException {
            onError(this.message)
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(Dispatchers.IO)


}