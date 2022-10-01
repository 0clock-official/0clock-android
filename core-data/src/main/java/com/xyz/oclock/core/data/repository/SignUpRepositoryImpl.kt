package com.xyz.oclock.core.data.repository

import com.skydoves.sandwich.*
import com.xyz.oclock.core.model.Token
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
            val token = Token(this.data.token)
            emit(token)
        }.onError {
            map(ErrorResponseMapper) { onError(this.response) }
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
            if (this.statusCode.code == 200) {
                emit(true)
            }
        }.onError {
            map(ErrorResponseMapper) { onError(this.response) }
        }.onException {
            onError(this.message)
        }
    }.onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(Dispatchers.IO)

}