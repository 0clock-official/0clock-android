package com.xyz.oclock.ui.signup.time

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import com.xyz.oclock.ui.signup.time.PreferredTime.*
import com.xyz.oclock.ui.signup.time.Sex.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class SignUpTimeViewModel @AssistedInject constructor(
    private val resourceProvider: ResourceProvider,
    @Assisted private val listener: SignUpViewPagerFragmentListener
): BindingViewModel() {

    @get:Bindable
    var toastMessage: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(::toastMessage)
        }

    @get: Bindable
    var timeArray by bindingProperty(PreferredTime.values().sortedBy{ it.index }.map { it.desc }.toTypedArray())

    @get: Bindable
    var partnerSexArray by bindingProperty(Sex.values().sortedBy{ it.index }.map{ it.desc }.toTypedArray())

    @get: Bindable
    var selectedTime: PreferredTime? by bindingProperty(null)

    @get: Bindable
    var selectedSex: Sex? by bindingProperty(null)

    fun setPreferredTime(position: Int) {
        selectedTime = PreferredTime.values().filter { it.desc == timeArray[position] }[0]
    }

    fun setPartnerSex(position: Int) {
        selectedSex = Sex.values().filter { it.desc == partnerSexArray[position] }[0]
    }

    fun onClickNextButton() {
        if (selectedTime != null && selectedSex != null) {
            listener.setChattingTimeOnSignUpViewModel(selectedTime!!.index)
            listener.setPartnerSexOnSignUpViewModel(selectedSex!!.index)
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

enum class PreferredTime(val index: Int, val desc: String) {
    PM_10(1, "오후 10:00 ㅤ- ㅤ오후 12:00"),
    PM_11(2, "오후 11:00 ㅤ- ㅤ오전 01:00"),
    PM_12(3, "오후 12:00 ㅤ- ㅤ오전 02:00"),
    AM_01(4, "오전 01:00 ㅤ- ㅤ오전 03:00")
}

enum class Sex(val index: Int, val desc: String) {
    MAN(1, "남자"),
    WOMAN(2, "여자"),
    NON(3, "상관없음")
}