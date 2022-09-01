package com.xyz.oclock.core.model

data class LoginStep(val step: Int) {
    fun isSignUpEnabled(): Boolean {
        return when(step) {
            1 -> true
            else -> false
        }
    }
}
