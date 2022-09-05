package com.xyz.oclock.ui.signup

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private var email: String? = null
    private var password: String? = null
    private var stdCard: Bitmap? = null

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(pw: String) {
        this.password = pw
    }

    fun setStdCard(bitmap: Bitmap) {
        this.stdCard = bitmap
    }
}