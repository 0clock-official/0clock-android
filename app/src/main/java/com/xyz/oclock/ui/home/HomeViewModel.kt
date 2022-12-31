package com.xyz.oclock.ui.home

import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
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
class HomeViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val tokenRepository: TokenRepository,
    private val logoutHelper: LogoutHelper,
    private val resourceProvider: ResourceProvider,
    private val deviceStateRepository: DeviceStateRepository
): BaseViewModel() {

    fun checkPendingState(
        onPending: suspend ()->Unit,
        onRejected: suspend ()->Unit,
        onApproved: suspend ()->Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        signUpRepository.checkStudentCardVerified(
            accessToken = token,
            onStart = { },
            onComplete = { },
            onError = {
                showToast(it)
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
                        showToast(it.message)
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