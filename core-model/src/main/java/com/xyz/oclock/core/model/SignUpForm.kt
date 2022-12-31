package com.xyz.oclock.core.model

data class SignUpForm (
    val email: String,
    val password: String,
    val nickname: String,
    val major: Int,
    val chattingTime: Int,
    val memberSex: Int,
    val matchingSex: Int,
    val fcmToken: String
)