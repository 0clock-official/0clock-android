package com.xyz.oclock.ui.signup.email

import androidx.databinding.Bindable
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpEmailViewModel @Inject constructor(): BindingViewModel() {

    @get: Bindable
    var emailErrorHint by bindingProperty("")

    @get: Bindable
    var inputEmail by bindingProperty("")

    @get: Bindable
    var inputVerifyCode by bindingProperty("")

    @get: Bindable
    var isEmailBlocked by bindingProperty(false)

    var verifyCode = "111111" // TODO 연동

}