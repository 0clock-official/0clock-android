package com.xyz.oclock.core.network.model.request

data class SignUpRequest (
    val email: String,
    val password: String,
    val nickname: String,
    val major: Int,
    val chattingTime: Int,
    val matchingSex: Int,
    val memberSex: Int,
    val fcmToken: String
)