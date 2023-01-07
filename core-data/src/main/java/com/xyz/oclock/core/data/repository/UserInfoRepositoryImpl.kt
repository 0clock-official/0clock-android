package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.database.SharedPreferences
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): UserInfoRepository {

    override fun getEmail(): String? {
        return sharedPreferences.getUserEmail()
    }

    override fun setEmail(email: String) {
        sharedPreferences.setUserEmail(email)
    }
}