package com.xyz.oclock.ui.pending

import androidx.lifecycle.viewModelScope
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.LogoutHelper
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.DeviceStateRepository
import com.xyz.oclock.core.data.repository.SignUpRepository
import com.xyz.oclock.core.data.repository.TokenRepository
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val tokenRepository: TokenRepository,
    private val logoutHelper: LogoutHelper,
    private val resourceProvider: ResourceProvider,
    private val deviceStateRepository: DeviceStateRepository
): BaseViewModel() {

    fun checkPendingState(
        onPending: suspend ()->Unit,
        onRejected: suspend ()->Unit,
        onApproved: suspend ()->Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        signUpRepository.checkStudentCardVerified(
            accessToken = token,
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = {
                onError(it?: resourceProvider.getString(R.string.unknown_error))
            }
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val result = it.data as Boolean
                    if (result) {
                        onApproved()
                    } else {
                        onRejected()
                    }
                }
                is CommonResponse.Fail ->  {
                    if (it.code == 401) {
                        logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
                    } else {
                        onError(it.message)
                    }
                }
            }
        }
    }

    fun isFirstLogin(): Boolean {
        return deviceStateRepository.isFirstLogin()
    }

    fun noLongerFirstLogin() {
        deviceStateRepository.noLongerFirstLogin()
    }
}