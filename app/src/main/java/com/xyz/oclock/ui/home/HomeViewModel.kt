package com.xyz.oclock.ui.home

import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.xyz.oclock.core.data.repository.DeviceStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceStateRepository: DeviceStateRepository
): BindingViewModel() {

    fun checkPendingState(
        onPending: suspend ()->Unit,
        onRejected: suspend ()->Unit,
        onApproved: suspend ()->Unit
    ) = viewModelScope.launch {
        onPending()
//        onRejected()
//        onApproved()
//        팬딩상태검사
//        pending, reject-> pendingFragment
//        approve -> { 처음? pendingFragment : main }
//        토큰만료 -> 로그인
    }


    fun isFirstLogin(): Boolean {
        return deviceStateRepository.isFirstLogin()
    }

    fun noLongerFirstLogin() {
        deviceStateRepository.noLongerFirstLogin()
    }
}