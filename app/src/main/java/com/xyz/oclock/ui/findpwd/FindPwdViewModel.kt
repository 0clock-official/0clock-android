package com.xyz.oclock.ui.findpwd

import androidx.databinding.Bindable
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindPwdViewModel @Inject constructor(

): BindingViewModel() {

    @get: Bindable
    var progress by bindingProperty(0)

}