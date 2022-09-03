package com.xyz.oclock.ui.signup.password

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import com.xyz.oclock.ui.signup.email.SignUpEmailViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SignUpPasswordViewModel @AssistedInject constructor(
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BindingViewModel() {

    @get: Bindable
    var password by bindingProperty("")

    @get: Bindable
    var passwordConfirm by bindingProperty("")

    fun setPasswordAndMoveToPending() = viewModelScope.launch {
        listener.setPasswordOnSignUpViewModel(password)
        listener.onNextButtonClicked()
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