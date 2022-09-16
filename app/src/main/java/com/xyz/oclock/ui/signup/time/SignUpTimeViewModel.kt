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
    var timeArray by bindingProperty( arrayOf(PM_10.desc, PM_11.desc, PM_12.desc, AM_01.desc))

    @get: Bindable
    var partnerSexArray by bindingProperty( arrayOf(MAN.desc, WOMAN.desc, NON.desc))

    @get: Bindable
    var selectedTime: String? by bindingProperty(null)

    @get: Bindable
    var selectedSex: String? by bindingProperty(null)

    fun setPreferredTime(position: Int) {
        selectedTime = timeArray[position]
    }

    fun setPartnerSex(position: Int) {
        selectedSex = partnerSexArray[position]
    }

    fun onClickNextButton() {

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
    AM_01(4, "오전 01:00 ㅤ- ㅤ오전 03:00"),
}

enum class Sex(val index: Int, val desc: String) {
    MAN(1, "남자"),
    WOMAN(2, "여자"),
    NON(3, "상관없음")
}