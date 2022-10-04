package com.xyz.oclock.ui.signup.stdcard

import com.xyz.oclock.common.utils.ResourceProvider
import android.graphics.Bitmap
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xyz.oclock.R
import com.xyz.oclock.ui.BaseViewModel
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


class SignUpStdCardViewModel @AssistedInject constructor(
    @Assisted private val listener: SignUpViewPagerFragmentListener,
    private val resourceProvider: ResourceProvider
): BaseViewModel() {

    @get:Bindable
    var stdCardImage: Bitmap? = null
        set(value) {
            field = value
            value?.let { listener.setStdCardOnSignUpViewModel(it) }
            notifyAllPropertiesChanged()
        }

    fun onClickNextButton() {
        listener.uploadStdCard(
            onStart = { listener.showLoading() },
            onComplete = { listener.hideLoading() },
            onError = { showToast(resourceProvider.getString(R.string.unknown_error)) },
            onSuccess = {
                submitSignUpForm()
            }
        )

    }

    private fun submitSignUpForm() {
        listener.submitSignUpForm(
            onStart = { listener.showLoading() },
            onComplete = { listener.hideLoading() },
            onError = { showToast(resourceProvider.getString(R.string.unknown_error)) },
            onSuccess = {
                listener.moveToPendingFragment()
            }
        )
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