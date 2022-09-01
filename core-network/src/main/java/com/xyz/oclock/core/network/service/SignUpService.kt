package com.xyz.oclock.core.network.service

import com.skydoves.sandwich.ApiResponse
import com.xyz.oclock.core.network.model.SignUpStepResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SignUpService {

    @GET("members/join/{email}/state")
    suspend fun checkEnabledEmail(@Path("email") name: String): ApiResponse<SignUpStepResponse>

}