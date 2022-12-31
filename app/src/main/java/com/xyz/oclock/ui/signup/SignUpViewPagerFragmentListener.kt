package com.xyz.oclock.ui.signup

import android.graphics.Bitmap
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.core.model.Sex

interface SignUpViewPagerFragmentListener {

    fun setEmailOnSignUpViewModel(email: String)
    fun setPasswordOnSignUpViewModel(pw: String)
    fun setNicknameOnSignUpViewModel(nickname: String)
    fun setMajorOnSignUpViewModel(major: Int)
    fun setChattingTimeOnSignUpViewModel(chattingTime: ChattingTime)
    fun setPartnerSexOnSignUpViewModel(partnerSex: Sex)
    fun setMySexOnSignUpViewModel(mySex: Sex)
    fun setStdCardOnSignUpViewModel(stdCard: Bitmap)

    fun showLoading()
    fun hideLoading()

    fun moveToNextStep()
    fun moveToPendingFragment()

    fun submitSignUpForm(
        onStart: ()->Unit,
        onComplete: ()->Unit,
        onSuccess: (String, String) -> Unit,
        onError: (String?)->Unit
    )
    fun uploadStdCard(
        onStart: ()->Unit,
        onComplete: ()->Unit,
        onSuccess: ()->Unit,
        onError: (String?)->Unit
    )
}