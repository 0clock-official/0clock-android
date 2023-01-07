package com.xyz.oclock.core.network.model.response

data class TokenResponse (
    val accessToken: String,
    val refreshToken: String
)