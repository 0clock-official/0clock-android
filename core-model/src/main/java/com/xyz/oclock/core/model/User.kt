package com.xyz.oclock.core.model

data class User (
    val email: String,
    val nickname: String,
    val major: Int,
    val memberSex: Int,
    val matchingSex: Int,
    val chattingTime: Int,
)