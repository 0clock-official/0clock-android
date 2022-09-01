package com.xyz.oclock.ui.signup.email

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEmailViewModel @Inject constructor(
    private val repository: SignUpRepository,
    private val resourceProvider: ResourceProvider
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

    var verifyCode = "111111" // TODO 연동

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

}