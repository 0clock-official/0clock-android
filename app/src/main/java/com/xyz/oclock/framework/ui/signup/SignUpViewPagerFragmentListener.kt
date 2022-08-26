package com.xyz.oclock.framework.ui.signup

interface SignUpViewPagerFragmentListener {
    fun onNextButtonClicked()
    fun sendEmailToNextPage(email: String)
    fun sendPasswordToNextPage(pw: String)
}