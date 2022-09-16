package com.xyz.oclock.ui.signup.password

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignUpPasswordViewModel @AssistedInject constructor(
    private val resourceProvider: ResourceProvider,
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BindingViewModel() {

    @get: Bindable
    var password = ""
        set(value) {
            field = value
            checkPasswordFormat(value)
            notifyPropertyChanged(::password)
            notifyPropertyChanged(::passwordErrorHint)
        }

    @get: Bindable
    var passwordErrorHint by bindingProperty("")

    @get: Bindable
    var passwordConfirm by bindingProperty("")

    fun onClickNextButton() = viewModelScope.launch {
        listener.setPasswordOnSignUpViewModel(password)
        listener.moveToNextStep()
    }

    private fun checkPasswordFormat(pw: String) {
        var pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"
        var pattern = Pattern.compile(pwPattern)
        if (pattern.matcher(pw).matches()) {
            passwordErrorHint = ""
        } else {
            if (pw.length < 8 || pw.length > 20) {
                passwordErrorHint = resourceProvider.getString(R.string.error_password_format_1)
                return
            }
            pwPattern = "([A-Za-z])"
            pattern = Pattern.compile(pwPattern)
            if (!pattern.matcher(pw).find()) {
                passwordErrorHint = resourceProvider.getString(R.string.error_password_format_3)
                return
            }
            pwPattern = "([0-9])"
            pattern = Pattern.compile(pwPattern)
            if (!pattern.matcher(pw).find()) {
                passwordErrorHint = resourceProvider.getString(R.string.error_password_format_2)
                return
            }
        }
    }


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(listener: SignUpViewPagerFragmentListener): SignUpPasswordViewModel
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