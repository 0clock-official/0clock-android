package com.xyz.oclock.ui

import androidx.databinding.Bindable
import com.skydoves.bindables.BindingViewModel

open class BaseViewModel: BindingViewModel() {

    @get:Bindable
    var toastMessage: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(::toastMessage)
        }

    fun showToast(message: String?) {
        toastMessage = message
    }
}