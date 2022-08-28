package com.xyz.oclock.ui.signup.stdcard

import android.graphics.Bitmap
import androidx.databinding.Bindable
import com.skydoves.bindables.BindingViewModel

class SignUpStdCardViewModel: BindingViewModel() {

    @get:Bindable
    var stdCardImage: Bitmap? = null
        set(value) {
            field = value
            notifyAllPropertiesChanged()
        }

}