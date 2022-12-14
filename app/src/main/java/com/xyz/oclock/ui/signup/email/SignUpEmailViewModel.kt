package com.xyz.oclock.ui.signup.email

import android.os.CountDownTimer
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.throttle
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.SignUpRepository
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.Token
import com.xyz.oclock.ui.BaseViewModel
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignUpEmailViewModel @AssistedInject constructor(
    private val repository: SignUpRepository,
    private val resourceProvider: ResourceProvider,
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BaseViewModel() {

    @get: Bindable
    var inputEmail: String = ""
        set(value) {
            field = value
            checkEmailFormat(value)
            notifyPropertyChanged(::emailErrorHint)
        }

    @get: Bindable
    var emailErrorHint by bindingProperty("")

    @get: Bindable
    var inputVerifyCode by bindingProperty("")

    @get:Bindable
    var verifyError by bindingProperty(false)

    @get: Bindable
    var isEmailBlocked by bindingProperty(false)

    @get: Bindable
    var remainingTimeSec by bindingProperty(0)

    var countDownTimer: CountDownTimer? = null

    fun sendVerifyCodeToEmail() = viewModelScope.launch {
        repository.sendVerifyCodeToEmail(
                    email = inputEmail,
                    onStart = { listener.showLoading() },
                    onComplete = { listener.hideLoading() },
                    onError = {  showToast(it) }
                ).collectLatest {
                    when (it) {
                        is CommonResponse.Success<*> -> {
                            showToast(it.message)
                            isEmailBlocked = true
                            clearVerifyCode()
                            newCountDownTimer()
                        }
                        is CommonResponse.Fail ->  emailErrorHint = it.message
                    }
                }
            }

            private fun clearVerifyCode() {
                inputVerifyCode = ""
                verifyError = false
            }

            private fun newCountDownTimer() {
                if (countDownTimer != null) {
                    countDownTimer!!.cancel()
                }
                countDownTimer = object : CountDownTimer(VERIFY_CODE_TIME_LIMIT, 1000L) {
                    override fun onTick(millisUntilFinished: Long) {
                        remainingTimeSec = (millisUntilFinished/1000).toInt()
                    }

                    override fun onFinish() {
                        showToast(resourceProvider.getString(R.string.required_re_verify))
                    }
                }.start()
            }

            private fun checkEmailFormat(email: String) {
                val pattern = Patterns.EMAIL_ADDRESS
                emailErrorHint = if (pattern.matcher(email).matches()) {
                    ""
                } else {
                    resourceProvider.getString(R.string.error_email_format)
                }
            }

            fun onClickNextButton() = viewModelScope.launch {
                repository.checkVerifyCode(
                    email = inputEmail,
                    code = inputVerifyCode,
                    onStart = { listener.showLoading() },
                    onComplete = { listener.hideLoading() },
                    onError = { showToast(it) }
                ).collectLatest {
                    when (it) {
                        is CommonResponse.Success<*> -> {
                            verifyError = false
                            listener.setEmailOnSignUpViewModel(inputEmail)
                            listener.moveToNextStep()
                        }
                        is CommonResponse.Fail -> {
                            verifyError = true
                        }
                    }
                }
            }

            @dagger.assisted.AssistedFactory
            interface AssistedFactory {
                fun create(listener: SignUpViewPagerFragmentListener): SignUpEmailViewModel
            }

            companion object {
                const val VERIFY_CODE_TIME_LIMIT = 300000L
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