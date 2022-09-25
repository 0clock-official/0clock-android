package com.xyz.oclock.ui.splash

import com.skydoves.bindables.BindingViewModel
import com.xyz.oclock.core.data.repository.DeviceStateRepository
import com.xyz.oclock.core.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
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
}