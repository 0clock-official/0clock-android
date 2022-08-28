package com.xyz.oclock.ui.signup

interface SignUpViewPagerFragmentListener {
    fun onNextButtonClicked()
    fun sendEmailToNextPage(email: String)
    fun sendPasswordToNextPage(pw: String)
}