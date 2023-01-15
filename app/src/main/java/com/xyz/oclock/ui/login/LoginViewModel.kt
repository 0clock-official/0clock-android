package com.xyz.oclock.ui.login

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.CommonRepository
import com.xyz.oclock.core.data.repository.DeviceStateRepository
import com.xyz.oclock.core.data.repository.TokenRepository
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val commonRepository: CommonRepository,
    private val tokenRepository: TokenRepository,
    private val deviceStateRepository: DeviceStateRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    @get: Bindable
    var email: String = ""

    @get: Bindable
    var password = ""

    fun isFirstRun(): Boolean {
        return deviceStateRepository.isFirstRun()
    }

    fun noLongerFirstRun() {
        deviceStateRepository.noLongerFirstRun()
    }

    fun login(onSuccess: ()->Unit) =  viewModelScope.launch {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("이메일과 비밀번호를 입력해주세요.")
            return@launch
        }
        commonRepository.login(
            email = email,
            password = password,
            onStart =  { showLoading() },
            onComplete = { hideLoading() },
            onError = {
                showToast(it?: resourceProvider.getString(R.string.unknown_error))
            }
        ).collectLatest {
            when (it) {
                is CommonResponse.Success<*> -> {
                    val pair = it.data as Pair<*, *>
                    val accessToken = pair.first as String
                    val refreshToken = pair.second as String
                    tokenRepository.setAccessToken("Bearer $accessToken")
                    tokenRepository.setRefreshToken("refreshToken:$refreshToken")
                    onSuccess()
                }
                is CommonResponse.Fail ->  {
                    showToast(it.message)
                }
            }
        }
    }
}