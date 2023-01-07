package com.xyz.oclock.core.data.repository

import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.network.model.mapper.ErrorResponseMapper
import com.xyz.oclock.core.network.service.CommonClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val commonClient: CommonClient
): CommonRepository {

    override fun getNewToken(
        refreshToken: String,
        onError: (String?) -> Unit
    ) = flow {
        val response = commonClient.getNewToken(refreshToken)
        response.suspendOnSuccess {
            emit(CommonResponse.Success(this.data.response, Any()))
        }.suspendOnError {
            val errorResponse = ErrorResponseMapper.map(this)
            emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
        }.onException {
            onError(null)
        }
    }.flowOn(Dispatchers.IO)

    override fun login(
        email: String,
        password: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = commonClient.login(email, password)
        response.suspendOnSuccess {
            val accessToken = this.data.data?.accessToken
            val refreshToken = this.data.data?.refreshToken
            if (accessToken != null && refreshToken != null) {
                val pair = Pair(accessToken, refreshToken)
                emit(CommonResponse.Success(data = pair))
            }
        }.suspendOnError {
            val errorResponse = ErrorResponseMapper.map(this)
            emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
        }.onException {
            onError(null)
        }
    }.onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(Dispatchers.IO)


}