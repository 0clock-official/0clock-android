package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.SignUpStepResponse
import javax.inject.Inject

class SignUpClient @Inject constructor(
    private val signUpService: SignUpService
) {

    suspend fun checkEnabledEmail(email: String): ApiResponse<SignUpStepResponse> {
        return signUpService.checkEnabledEmail(email)
    }


}