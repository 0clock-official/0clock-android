package com.xyz.oclock.ui

import androidx.databinding.Bindable
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty

open class BaseViewModel: BindingViewModel() {

    @get:Bindable
    var toastMessage: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(::toastMessage)
        }

    @get: Bindable
    var isLoading by bindingProperty(false)

    fun showToast(message: String?) {
        toastMessage = message
    }

    fun showLoading() {
        isLoading = true
    }

    fun hideLoading() {
        isLoading = false
    }

}