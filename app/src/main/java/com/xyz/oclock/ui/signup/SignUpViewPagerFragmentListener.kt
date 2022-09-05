package com.xyz.oclock.ui.signup

interface SignUpViewPagerFragmentListener {

    suspend fun moveToNextStep()

    suspend fun setEmailOnSignUpViewModel(email: String)

    suspend fun setPasswordOnSignUpViewModel(pw: String)

}