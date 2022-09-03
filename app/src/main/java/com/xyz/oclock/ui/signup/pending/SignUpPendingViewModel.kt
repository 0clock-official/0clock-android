package com.xyz.oclock.ui.signup.pending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skydoves.bindables.BindingViewModel
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import com.xyz.oclock.ui.signup.password.SignUpPasswordViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class SignUpPendingViewModel @AssistedInject constructor(
    @Assisted private val listener: SignUpViewPagerFragmentListener
) : BindingViewModel() {


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(listener: SignUpViewPagerFragmentListener): SignUpPendingViewModel
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