package com.xyz.oclock.ui.signup.stdcard

import android.graphics.Bitmap
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skydoves.bindables.BindingViewModel
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class SignUpStdCardViewModel @AssistedInject constructor(
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BindingViewModel() {

    @get:Bindable
    var stdCardImage: Bitmap? = null
        set(value) {
            field = value
            notifyAllPropertiesChanged()
        }

    fun onClickNextButton() {
        listener.submitSignUpForm()
        listener.moveToPendingFragment()
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(listener: SignUpViewPagerFragmentListener): SignUpStdCardViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            listener: SignUpViewPagerFragmentListener
        ) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(listener) as T
            }
        }
    }
}