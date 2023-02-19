package com.xyz.oclock.core.network.model.request

data class EditProfileRequest(
    val nickname: String,
    val chattingTime: String,
    val matchingSex: String
)