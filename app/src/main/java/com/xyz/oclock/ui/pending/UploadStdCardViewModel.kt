package com.xyz.oclock.ui.pending

import android.graphics.Bitmap
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.SignUpRepository
import com.xyz.oclock.core.data.repository.TokenRepository
import com.xyz.oclock.core.data.repository.UserInfoRepository
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.Network
import com.xyz.oclock.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadStdCardViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val userInfoRepository: UserInfoRepository,
    private val tokenRepository: TokenRepository,
    private val resourceProvider: ResourceProvider,
): BaseViewModel() {

    @get:Bindable
    var stdCardImage: Bitmap? = null
        set(value) {
            field = value
            notifyAllPropertiesChanged()
        }

    fun uploadStdCard(
        onSuccess: ()->Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            showToast(resourceProvider.getString(R.string.unknown_error))
        } else {
            if (userInfoRepository.getEmail() != null && stdCardImage != null) {
                signUpRepository.uploadStdCard(
                    email = userInfoRepository.getEmail()!!,
                    stdCard = stdCardImage!!,
                    accessToken = token,
                    onStart = { showLoading() },
                    onComplete = { hideLoading() },
                    onError = { msg ->
                        showToast(msg?: resourceProvider.getString(R.string.unknown_error))
                    }
                ).collectLatest {
                    when (it) {
                        is CommonResponse.Success<*> -> {
                            onSuccess()
                        }
                        is CommonResponse.Fail ->  {
                            if (it.code == Network.UNAUTHORIZED.code) {

                            } else {
                                showToast(it.message)
                            }
                        }
                    }
                }
            }
        }
    }

}