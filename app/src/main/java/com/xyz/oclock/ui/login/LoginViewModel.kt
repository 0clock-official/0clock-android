package com.xyz.oclock.ui.login

import androidx.lifecycle.ViewModel
import com.xyz.oclock.core.data.repository.DeviceStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val deviceStateRepository: DeviceStateRepository
) : ViewModel() {

    fun isFirstRun(): Boolean {
        return deviceStateRepository.isFirstRun()
    }

    fun noLongerFirstRun() {
        deviceStateRepository.noLongerFirstRun()
    }
}