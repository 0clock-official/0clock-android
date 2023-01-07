package com.xyz.oclock.core.data.repository

interface UserInfoRepository {
    fun getEmail(): String?
    fun setEmail(email: String)
}