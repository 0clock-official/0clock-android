package com.xyz.oclock.core.data.repository

import android.graphics.Bitmap
import com.skydoves.sandwich.*
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.SignUpForm
import com.xyz.oclock.core.model.StdCardStatus
import com.xyz.oclock.core.network.model.mapper.ErrorResponseMapper
import com.xyz.oclock.core.network.service.SignUpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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
            emit(CommonResponse.Success<Nothing>())
        }.suspendOnError {
            try {
                val errorResponse = ErrorResponseMapper.map(this)
                emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
            } catch (e: Exception) {
                onError(null)
            }
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
            emit(CommonResponse.Success<Nothing>(this.data.response))
        }.suspendOnError {
            try {
                val errorResponse = ErrorResponseMapper.map(this)
                emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
            } catch (e: Exception) {
                onError(null)
            }
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
            emit(CommonResponse.Success<Nothing>())
        }.suspendOnError {
            try {
                val errorResponse = ErrorResponseMapper.map(this)
                emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
            } catch (e: Exception) {
                onError(null)
            }
        }.onException {
            onError(this.message)
        }
    }.onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(Dispatchers.IO)

    override fun uploadStdCard(
        email: String,
        stdCard: Bitmap,
        accessToken: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) =  flow {
        val response = signUpClient.uploadStdCard(email, stdCard, accessToken)
        response.suspendOnSuccess {
            emit(CommonResponse.Success<Nothing>())
        }.suspendOnError {
            try {
                val errorResponse = ErrorResponseMapper.map(this)
                emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
            } catch (e: Exception) {
                onError(null)
            }
        }.onException {
            onError(null)
        }
    }.onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(Dispatchers.IO)

    override fun signUp(
        signUpForm: SignUpForm,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = signUpClient.signUp(signUpForm)
        response.suspendOnSuccess {
            val accessToken = this.data.data?.accessToken
            val refreshToken = this.data.data?.refreshToken
            if (accessToken != null && refreshToken != null) {
                val pair = Pair(accessToken, refreshToken)
                emit(CommonResponse.Success(data = pair))
            }
        }.suspendOnError {
            try {
                val errorResponse = ErrorResponseMapper.map(this)
                emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
            } catch (e: Exception) {
                onError(null)
            }
        }.onException {
            onError(null)
        }
    }.onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(Dispatchers.IO)

    override fun checkStudentCardVerified(
        accessToken: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = signUpClient.checkStudentCardVerified(accessToken)
        response.suspendOnSuccess {
            when(this.data.data) {
                StdCardStatus.INVALID.status -> {
                    emit(CommonResponse.Success<StdCardStatus>(this.data.response, StdCardStatus.INVALID))
                }
                StdCardStatus.VALID.status -> {
                    emit(CommonResponse.Success<StdCardStatus>(this.data.response, StdCardStatus.VALID))
                }
                StdCardStatus.PENDING.status -> {
                    emit(CommonResponse.Success<StdCardStatus>(this.data.response, StdCardStatus.PENDING))
                }
                else -> {
                    onError(null)
                }
            }
        }.suspendOnError {
            try {
                val errorResponse = ErrorResponseMapper.map(this)
                emit(CommonResponse.Fail(errorResponse.message, errorResponse.code))
            } catch (e: Exception) {
                onError(null)
            }
        }.onException {
            onError(null)
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(Dispatchers.IO)

}