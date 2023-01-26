package com.xyz.oclock.ui.signup.stdcard

import com.xyz.oclock.common.utils.ResourceProvider
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.LogoutHelper
import com.xyz.oclock.core.data.repository.TokenRepository
import com.xyz.oclock.ui.BaseViewModel
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.ByteArrayOutputStream

class SignUpStdCardViewModel @AssistedInject constructor(
    @Assisted private val listener: SignUpViewPagerFragmentListener,
    private val tokenRepository: TokenRepository,
    private val resourceProvider: ResourceProvider,
): BaseViewModel() {

    @get:Bindable
    var stdCardImage: Bitmap? = null
        set(value) {
            field = value
            value?.let { listener.setStdCardOnSignUpViewModel(it) }
            notifyAllPropertiesChanged()
        }

    fun onClickNextButton() {
        submitSignUpForm()
    }

    private fun submitSignUpForm() {
        listener.submitSignUpForm(
            onStart = { listener.showLoading() },
            onComplete = { listener.hideLoading() },
            onError = { message->
                viewModelScope.launch {
                    if (message == null) {
                        showToast(resourceProvider.getString(R.string.unknown_error))
                    } else {
                        showToast(message)
                    }
                }
            },
            onSuccess = { accessToken, refreshToken ->
                tokenRepository.setAccessToken(accessToken)
                tokenRepository.setRefreshToken("refreshToken:$refreshToken")
                uploadStdCard()
            }
        )
    }

    private fun uploadStdCard() {
        listener.uploadStdCard(
            onStart = { listener.showLoading() },
            onComplete = { listener.hideLoading() },
            onError = { message ->
                viewModelScope.launch {
                    if (message == null) {
                        showToast(resourceProvider.getString(R.string.unknown_error))
                    } else {
                        showToast(message)
                    }
                }
            },
            onSuccess = {
                listener.moveToPendingFragment()
            }
        )
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(listener: SignUpViewPagerFragmentListener): SignUpStdCardViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            listener: SignUpViewPagerFragmentListener
        ) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(listener) as T
            }
        }
    }
}