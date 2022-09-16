package com.xyz.oclock.ui.signup.major

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch


class SignUpMajorViewModel @AssistedInject constructor(
    private val resourceProvider: ResourceProvider,
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BindingViewModel() {

    @get: Bindable
    var majors by bindingProperty(arrayOf("경영학과", "물리학과", "철학과", "경영학과", "물리학과", "철학과", "경영학과", "물리학과", "철학과", "경영학과", "물리학과", "철학과", "경영학과", "물리학과", "철학과"))

    @get:Bindable
    var toastMessage: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(::toastMessage)
        }

    @get:Bindable
    var selectedMajor: String? by bindingProperty(null)

    fun setUserSelectedMajor(p: Int) {
        selectedMajor = majors[p]
    }

    fun onClickNextButton() {

    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(listener: SignUpViewPagerFragmentListener): SignUpMajorViewModel
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