package com.xyz.oclock.core.data.repository

import com.skydoves.sandwich.*
import com.xyz.oclock.core.model.LoginStep
import com.xyz.oclock.core.network.model.ErrorResponseMapper
import com.xyz.oclock.core.network.service.SignUpClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpClient: SignUpClient
): SignUpRepository {

    override suspend fun checkEnabledEmail(email: String, onError: (String?)->Unit): LoginStep {
        val response = signUpClient.checkEnabledEmail(email)
        return suspendCancellableCoroutine { continuation ->
            response.onSuccess {
                continuation.resume(LoginStep(data.step)) {
                    onError(it.message)
                }
            }.onError {
                map(ErrorResponseMapper) { onError("[Code: $code]: $message") }
            }.onException {
                onError(message)
            }
        }
    }

    override suspend fun checkVerifyCode(email: String, verifyCode: String): Boolean {
        TODO("Not yet implemented")
    }

}