package com.xyz.oclock.core.data.repository

import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import com.xyz.oclock.core.model.*
import com.xyz.oclock.core.network.model.mapper.ErrorResponseMapper
import com.xyz.oclock.core.network.service.ChatClient
import com.xyz.oclock.core.network.util.WebServicesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatClient: ChatClient,
    private val webServicesProvider: WebServicesProvider
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


    override fun getServerTime(token: String): Flow<CommonResponse> = flow {
        // 내 정보 가져오는 api의 헤더값으로 시간받음
        val response = chatClient.getMyInfo(token)
        response.suspendOnSuccess {
            val data = this.data.data
            data?.let {
                val date = this.headers["Date"].convertToLocalCalendar()
                emit(CommonResponse.Success("Date", date))
            }
        }.suspendOnError {
            val date = this.headers["Date"].convertToLocalCalendar()
            emit(CommonResponse.Success("Date", date))
        }
    }.flowOn(Dispatchers.IO)

    override fun openSocket(): Channel<SocketChat> {
        return webServicesProvider.startSocket()
    }

    override fun closeSocket() {
        webServicesProvider.stopSocket()
    }

    override fun sendMessage(token: String, message: String, chatType: SocketChatType) {
        webServicesProvider.sendMessage(token, message, chatType)
    }

    private fun String?.convertToLocalCalendar(): Calendar? {
        // Tue, 24 Jan 2023 06:55:42 GMT
        val formatter = SimpleDateFormat("EEE',' dd MMM yyyy HH:mm:ss 'GMT'", Locale.UK)
        return try {
            val date = formatter.parse(this!!)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.HOUR_OF_DAY, +9)
            calendar
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

}