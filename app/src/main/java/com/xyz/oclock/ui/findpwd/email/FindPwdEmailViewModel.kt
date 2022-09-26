package com.xyz.oclock.ui.findpwd.email

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPwdEmailViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
): BindingViewModel() {

    @get: Bindable
    var inputEmail: String = ""
        set(value) {
            field = value
            checkEmailFormat(value)
            notifyPropertyChanged(::emailErrorHint)
        }

    @get: Bindable
    var isLoading by bindingProperty(false)

    @get: Bindable
    var emailErrorHint by bindingProperty("")

    @get: Bindable
    var inputVerifyCode by bindingProperty("")

    @get:Bindable
    var verifyError by bindingProperty(false)

    @get: Bindable
    var isEmailBlocked by bindingProperty(false)

    @get:Bindable
    var toastMessage: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(::toastMessage)
        }

    fun checkEnabledEmail() = viewModelScope.launch {
        isEmailBlocked = true
    }

    private fun checkEmailFormat(email: String) {
        val pattern = Patterns.EMAIL_ADDRESS
        emailErrorHint = if (pattern.matcher(email).matches()) {
            ""
        } else {
            resourceProvider.getString(R.string.error_email_format)
        }
    }
}