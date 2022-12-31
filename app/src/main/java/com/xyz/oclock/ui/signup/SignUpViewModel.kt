package com.xyz.oclock.ui.signup

import android.graphics.Bitmap
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.google.android.datatransport.runtime.backends.BackendResponse
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.core.data.repository.SignUpRepository
import com.xyz.oclock.core.data.repository.TokenRepository
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.core.model.CommonResponse
import com.xyz.oclock.core.model.Sex
import com.xyz.oclock.core.model.SignUpForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val tokenRepository: TokenRepository
) : BindingViewModel()  {

    private var email: String? = null
    private var password: String? = null
    private var nickname: String? = null
    private var major: Int? = null
    private var chattingTime: ChattingTime? = null
    private var matchingSex: Sex? = null
    private var memberSex: Sex? = null
    private var stdCard: Bitmap? = null

    @get: Bindable
    var isLoading by bindingProperty(false)

    @get: Bindable
    var progress by bindingProperty(0)

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(pw: String) {
        this.password = pw
    }

    fun setStdCard(bitmap: Bitmap) {
        this.stdCard = bitmap
    }

    fun setNickname(nickname: String) {
        this.nickname = nickname
    }

    fun setMajor(major: Int) {
        this.major = major
    }

    fun setChattingTime(chattingTime: ChattingTime) {
        this.chattingTime = chattingTime
    }

    fun setPartnerSex(partnerSex: Sex) {
        this.matchingSex = partnerSex
    }

    fun setMySex(mySex: Sex) {
        this.memberSex = mySex
    }

    fun submitSignUpForm(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onSuccess: (String, String) -> Unit,
        onError: (String?) -> Unit
    ) = viewModelScope.launch {
        val signUpForm = SignUpForm(
            email = email!!,
            password = password!!,
            nickname = nickname!!,
            major = 1,
            chattingTime = chattingTime!!.index,
            matchingSex = matchingSex!!.index,
            memberSex = memberSex!!.index,
            fcmToken = tokenRepository.getFcmToken()!!
        )
        signUpRepository.signUp(signUpForm, onStart, onComplete, onError)
            .collectLatest {
                when (it) {
                    is CommonResponse.Success<*> -> {
                        val pair = it.data as Pair<*, *>
                        onSuccess(pair.first as String, pair.second as String)
                    }
                    is CommonResponse.Fail ->  {
                        onError(it.message)
                    }
                }
            }
    }

    fun uploadStdCard(
        onStart: ()->Unit,
        onComplete: ()->Unit,
        onSuccess: ()->Unit,
        onError: (String?)->Unit
    ) = viewModelScope.launch {
        val token = tokenRepository.getAccessToken()
        if (token == null) {
            onError(null)
        } else {
            if (email != null && stdCard != null) {
                signUpRepository.uploadStdCard(
                    email = email!!,
                    stdCard = stdCard!!,
                    accessToken = token,
                    onStart = onStart,
                    onComplete = onComplete,
                    onError = onError
                ).collectLatest {
                    when (it) {
                        is CommonResponse.Success<*> -> {
                            onSuccess()
                        }
                        is CommonResponse.Fail ->  {
                            if (it.code == 401) {
                                onError("유효하지 않은 토큰입니다.")
                            } else {
                                onError(it.message)
                            }
                        }
                    }
                }
            }
        }
    }
}