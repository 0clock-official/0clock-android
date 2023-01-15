package com.xyz.oclock.ui.home

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.LogoutHelper
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.*
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.MatchingUser
import com.xyz.oclock.core.model.User
import com.xyz.oclock.ui.BaseViewModel
import com.xyz.oclock.ui.dialog.DefaultDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val tokenRepository: TokenRepository,
    private val chatRepository: ChatRepository,
    private val commonRepository: CommonRepository,
    private val logoutHelper: LogoutHelper,
    private val resourceProvider: ResourceProvider,
    private val deviceStateRepository: DeviceStateRepository
): BaseViewModel() {

    fun getNewToken() = viewModelScope.launch {
        val token = tokenRepository.getRefreshToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        commonRepository.getNewToken(
            refreshToken = token,
            onError = {}
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val pair = it.data as Pair<*, *>
                    val accessToken = pair.first as String
                    val refreshToken = pair.second as String
                    tokenRepository.setAccessToken(accessToken)
                    tokenRepository.setRefreshToken(refreshToken)
                }
                is CommonResponse.Fail ->  {
                    showToast(it.message)
                }
            }
        }
    }

    fun startMatching(
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
        onAlreadyMatched: () -> Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        chatRepository.startMatching(
            token = token,
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = {
                onFail(it?: resourceProvider.getString(R.string.unknown_error))
            },
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val roomId = it.data as Int
                    onSuccess()
                }
                is CommonResponse.Fail ->  {
                    when (it.code) {
                        401 -> {
                            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
                        }
                        404 -> {
                            onFail(it.message)
                        }
                        409 -> {
                            onAlreadyMatched()
                        }
                        else -> {
                            onFail(it.message)
                        }
                    }
                }
            }
        }
    }

    fun getMatchingUserInfo(
        onSuccess: (MatchingUser) -> Unit,
        onFail: () -> Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        chatRepository.getMatchingUserInfo(
            token = token,
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = {
                showToast(it?: resourceProvider.getString(R.string.unknown_error))
            },
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val matchingUser = it.data as MatchingUser
                    onSuccess(matchingUser)
                }
                is CommonResponse.Fail ->  {
                    when (it.code) {
                        401 -> {
                            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
                        }
                        404 -> {
                            showToast(it.message)
                        }
                        409 -> {
                            showToast(it.message)
                        }
                        else -> {
                            showToast(it.message)
                        }
                    }
                }
            }
        }
    }

    fun getMyInfo(
        onSuccess: (User) -> Unit,
        onFail: () -> Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
            return@launch
        }
        chatRepository.getMyInfo(
            token = token,
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = {
                showToast(it?: resourceProvider.getString(R.string.unknown_error))
            },
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val user = it.data as User
                    onSuccess(user)
                }
                is CommonResponse.Fail ->  {
                    when (it.code) {
                        401 -> {
                            logoutHelper.logout(resourceProvider.getString(R.string.forced_logout))
                        }
                        404 -> {
                            showToast(it.message)
                            onFail()
                        }
                        409 -> {
                            showToast(it.message)
                            onFail()
                        }
                        else -> {
                            showToast(it.message)
                            onFail()
                        }
                    }
                }
            }
        }
    }
}