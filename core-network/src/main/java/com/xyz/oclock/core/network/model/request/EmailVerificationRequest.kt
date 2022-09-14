package com.xyz.oclock.core.network.model.request

data class EmailVerificationRequest(
    val email: String,
    val code: String
)
