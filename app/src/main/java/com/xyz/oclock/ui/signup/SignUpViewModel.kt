package com.xyz.oclock.ui.signup

import android.graphics.Bitmap
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : BindingViewModel()  {

    private var email: String? = null
    private var password: String? = null
    private var stdCard: Bitmap? = null

    @get: Bindable
    var isLoading by bindingProperty(false)

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