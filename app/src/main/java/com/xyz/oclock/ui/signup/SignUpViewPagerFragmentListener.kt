package com.xyz.oclock.ui.signup

interface SignUpViewPagerFragmentListener {

    suspend fun onNextButtonClicked()

    suspend fun setEmailOnSignUpViewModel(email: String)

    suspend fun setPasswordOnSignUpViewModel(pw: String)

}