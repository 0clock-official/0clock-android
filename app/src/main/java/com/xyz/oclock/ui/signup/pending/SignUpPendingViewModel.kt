package com.xyz.oclock.ui.signup.pending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skydoves.bindables.BindingViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class SignUpPendingViewModel @AssistedInject constructor(
    @Assisted private val pendingState: PendingState
) : BindingViewModel() {

    fun isRequiredReCertifying():Boolean {
        return pendingState == PendingState.REJECT
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(pendingState: PendingState): SignUpPendingViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            pendingState: PendingState
        ) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(pendingState) as T
            }
        }
    }
}