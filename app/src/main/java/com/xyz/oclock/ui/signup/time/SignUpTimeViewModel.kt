package com.xyz.oclock.ui.signup.time

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.model.ChattingTime
import com.xyz.oclock.core.model.Sex
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class SignUpTimeViewModel @AssistedInject constructor(
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BindingViewModel() {

    @get:Bindable
    var toastMessage: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(::toastMessage)
        }

    @get: Bindable
    var timeArray by bindingProperty(ChattingTime.values().sortedBy{ it.index }.map { it.desc }.toTypedArray())

    @get: Bindable
    var partnerSexArray by bindingProperty(Sex.values().sortedBy{ it.index }.map{ it.desc }.toTypedArray())

    @get: Bindable
    var mySexArray by bindingProperty(Sex.values().sortedBy{ it.index }.map{ it.desc }.toTypedArray())

    @get: Bindable
    var selectedTime: ChattingTime? by bindingProperty(null)

    @get: Bindable
    var selectedMySex: Sex? by bindingProperty(null)

    @get: Bindable
    var selectedPartnerSex: Sex? by bindingProperty(null)

    fun setPreferredTime(position: Int) {
        selectedTime = ChattingTime.values().filter { it.desc == timeArray[position] }[0]
    }

    fun setPartnerSex(position: Int) {
        selectedPartnerSex = Sex.values().filter { it.desc == partnerSexArray[position] }[0]
    }

    fun setMySex(position: Int) {
        selectedMySex = Sex.values().filter { it.desc == mySexArray[position] }[0]
    }

    fun onClickNextButton() {
        if (selectedTime != null && selectedPartnerSex != null && selectedMySex != null) {
            listener.setChattingTimeOnSignUpViewModel(selectedTime!!)
            listener.setPartnerSexOnSignUpViewModel(selectedPartnerSex!!)
            listener.setMySexOnSignUpViewModel(selectedMySex!!)
            listener.moveToNextStep()
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(listener: SignUpViewPagerFragmentListener): SignUpTimeViewModel
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