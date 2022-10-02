package com.xyz.oclock.core.data.repository

import com.skydoves.sandwich.*
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.network.model.mapper.ErrorResponseMapper
import com.xyz.oclock.core.network.service.SignUpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpClient: SignUpClient
): SignUpRepository {

    override fun checkVerifyCode(
        email: String,
        code: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = signUpClient.checkVerifyCode(email, code)
        response.suspendOnSuccess {
            emit(CommonResponse.Success)
        }.suspendOnError {
            val errorMessage = ErrorResponseMapper.map(this).response
            emit(CommonResponse.Fail(errorMessage))
        }.onException {
            onError(this.message)
        }
    }.onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(Dispatchers.IO)

    override fun sendVerifyCodeToEmail(
        email: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = signUpClient.sendVerifyCodeToEmail(email)
        response.suspendOnSuccess {
            emit(CommonResponse.Success)
        }.suspendOnError {
            val errorMessage = ErrorResponseMapper.map(this).response
            emit(CommonResponse.Fail(errorMessage))
        }.onException {
            onError(this.message)
        }
    }.onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(Dispatchers.IO)

    override fun checkNicknameDuplication(
        nickname: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = signUpClient.checkNicknameDuplication(nickname)
        response.suspendOnSuccess {
            emit(CommonResponse.Success)
        }.suspendOnError {
            val errorMessage = ErrorResponseMapper.map(this).response
            emit(CommonResponse.Fail(errorMessage))
        }.onException {
            onError(this.message)
        }
    }.onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(Dispatchers.IO)

}