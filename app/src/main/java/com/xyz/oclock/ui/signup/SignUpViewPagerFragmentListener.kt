package com.xyz.oclock.ui.signup

interface SignUpViewPagerFragmentListener {

    fun moveToNextStep()

    fun setEmailOnSignUpViewModel(email: String)

    fun setPasswordOnSignUpViewModel(pw: String)

    fun showLoading()

    fun hideLoading()

}