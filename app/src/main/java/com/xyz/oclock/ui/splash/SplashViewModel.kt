package com.xyz.oclock.ui.splash

import com.skydoves.bindables.BindingViewModel
import com.xyz.oclock.core.data.repository.DeviceStateRepository
import com.xyz.oclock.core.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SplashViewModel constructor(
    private val tokenRepository: TokenRepository,
    private val deviceStateRepository: DeviceStateRepository
): BindingViewModel() {

    suspend fun checkLoginState(
        onLogin: suspend ()->Unit,
        onLogout: suspend ()->Unit
    ) {
        val isTokenExist = (tokenRepository.getAccessToken() != null) && (tokenRepository.getRefreshToken() != null)
        if (isTokenExist) {
            onLogin.invoke()
        } else {
            onLogout.invoke()
        }
    }

    suspend fun checkPendingState(
        onPending: suspend ()->Unit,
        onRejected: suspend ()->Unit,
        onApproved: suspend ()->Unit
    ) {
//        팬딩상태검사
//        pending, reject-> pendingFragment
//        approve -> { 처음? pendingFragment : main }
//        토큰만료 -> 로그인
    }

    fun isFirstRun(): Boolean {
        return deviceStateRepository.isFirstRun()
    }

    fun isFirstLogin(): Boolean {
        return deviceStateRepository.isFirstLogin()
    }
}