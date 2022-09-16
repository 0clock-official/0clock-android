package com.xyz.oclock.ui.signup.nickname

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

class SignUpNicknameViewModel @AssistedInject constructor(
    private val resourceProvider: ResourceProvider,
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BindingViewModel() {

    @get:Bindable
    var toastMessage: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(::toastMessage)
        }

    @get: Bindable
    var inputNickname: String = ""
        set(value) {
            field = value
            checkNicknameFormat(value)
            notifyPropertyChanged(::nicknameFormatError)
        }

    @get: Bindable
    var nicknameFormatError by bindingProperty("")

    fun onClickNextButton() = viewModelScope.launch {
        listener.setNicknameOnSignUpViewModel(inputNickname)
        listener.moveToNextStep()
//        nicknameFormatError = resourceProvider.getString(R.string.error_nickname_format4)
    }

    private fun checkNicknameFormat(nickname: String) {
        var nicknamePattern = "^[A-Za-z가-힣]{1,10}\$"
        var pattern = Pattern.compile(nicknamePattern)
        if (pattern.matcher(nickname).matches()) {
            nicknameFormatError = ""
        } else {
            if (nickname.isEmpty() || nickname.length > 10) {
                nicknameFormatError = resourceProvider.getString(R.string.error_nickname_format1)
                return
            }
            nicknamePattern = "([0-9])"
            pattern = Pattern.compile(nicknamePattern)
            if (pattern.matcher(nickname).find()) {
                nicknameFormatError = resourceProvider.getString(R.string.error_nickname_format2)
            } else {
                nicknameFormatError = resourceProvider.getString(R.string.error_nickname_format3)
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(listener: SignUpViewPagerFragmentListener): SignUpNicknameViewModel
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