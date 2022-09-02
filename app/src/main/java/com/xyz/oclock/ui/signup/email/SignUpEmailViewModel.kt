package com.xyz.oclock.ui.signup.email

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.SignUpRepository
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpEmailViewModel @AssistedInject constructor(
    private val repository: SignUpRepository,
    private val resourceProvider: ResourceProvider,
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BindingViewModel() {

    @get: Bindable
    var emailErrorHint by bindingProperty("")

    @get: Bindable
    var inputEmail by bindingProperty("")

    @get: Bindable
    var inputVerifyCode by bindingProperty("")

    @get: Bindable
    var isEmailBlocked by bindingProperty(false)

    @get:Bindable
    var toastMessage: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(::toastMessage)
        }

    @get:Bindable
    var verifyError by bindingProperty(false)

    fun checkEnabledEmail(onEnabled: ()->Unit) = viewModelScope.launch {
        val enabled = repository.checkEnabledEmail(inputEmail, onError = {
            toastMessage = it?: resourceProvider.getString(R.string.unknownError)
        }).isSignUpEnabled()
        if (!enabled) {
            emailErrorHint = resourceProvider.getString(R.string.error_already_signed_up_email)
        } else {
            isEmailBlocked = true
            onEnabled()
        }
    }

    fun checkVerifyCode() = viewModelScope.launch {
        val verified = repository.checkVerifyCode(inputEmail, inputVerifyCode)
        if (verified) {
            verifyError = false
            listener.onNextButtonClicked()
        } else {
            verifyError = true
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(listener: SignUpViewPagerFragmentListener): SignUpEmailViewModel
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