package com.xyz.oclock.ui.signup

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

    fun showLoading()
    fun hideLoading()
    fun moveToNextStep()
    fun submitSignUpForm()
    fun moveToPendingFragment()
}