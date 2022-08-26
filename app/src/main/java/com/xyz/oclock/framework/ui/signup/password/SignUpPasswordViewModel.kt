package com.xyz.oclock.framework.ui.signup.password

import androidx.databinding.Bindable
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty

class SignUpPasswordViewModel: BindingViewModel() {

    @get: Bindable
    var password by bindingProperty("")

    @get: Bindable
    var passwordConfirm by bindingProperty("")

}