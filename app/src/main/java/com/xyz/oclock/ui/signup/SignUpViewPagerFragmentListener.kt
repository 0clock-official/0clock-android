package com.xyz.oclock.ui.signup

interface SignUpViewPagerFragmentListener {

    fun setEmailOnSignUpViewModel(email: String)
    fun setPasswordOnSignUpViewModel(pw: String)
    fun setNicknameOnSignUpViewModel(nickname: String)
    fun setMajorOnSignUpViewModel(major: Int)
    fun setChattingTimeOnSignUpViewModel(chattingTime: Int)
    fun setPartnerSexOnSignUpViewModel(partnerSex: Int)

    fun showLoading()
    fun hideLoading()
    fun moveToNextStep()
    fun submitSignUpForm()
}