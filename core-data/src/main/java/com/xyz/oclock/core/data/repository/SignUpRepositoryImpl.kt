package com.xyz.oclock.core.data.repository

import com.skydoves.sandwich.*
import com.xyz.oclock.core.model.LoginStep
import com.xyz.oclock.core.network.model.ErrorResponseMapper
import com.xyz.oclock.core.network.service.SignUpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
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

}