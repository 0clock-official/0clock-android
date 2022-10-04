package com.xyz.oclock.ui.signup

import android.graphics.Bitmap
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.core.data.repository.SignUpRepository
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.core.model.Sex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : BindingViewModel()  {

    private var email: String? = null
    private var password: String? = null
    private var nickname: String? = null
    private var major: Int? = null
    private var chattingTime: ChattingTime? = null
    private var partnerSex: Sex? = null
    private var mySex: Sex? = null
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
        this.partnerSex = partnerSex
    }

    fun setMySex(mySex: Sex) {
        this.mySex = mySex
    }

    fun submitSignUpForm(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {

    }

    fun uploadStdCard(
        onStart: ()->Unit,
        onComplete: ()->Unit,
        onSuccess: ()->Unit,
        onError: ()->Unit
    ) = viewModelScope.launch {
        if (email != null && stdCard != null) {
            signUpRepository.uploadStdCard(
                email = email!!,
                stdCard = stdCard!!,
                onStart = onStart,
                onComplete = onComplete,
                onError = onError
            ).collectLatest {
                onSuccess()
            }
        }
    }
}