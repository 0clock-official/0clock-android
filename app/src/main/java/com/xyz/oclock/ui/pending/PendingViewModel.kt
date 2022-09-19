package com.xyz.oclock.ui.pending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skydoves.bindables.BindingViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class PendingViewModel @AssistedInject constructor(
    @Assisted private val pendingState: PendingState
) : BindingViewModel() {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(pendingState: PendingState): PendingViewModel
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