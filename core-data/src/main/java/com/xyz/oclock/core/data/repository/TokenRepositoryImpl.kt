package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.database.SharedPreferences
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): TokenRepository {

    override fun setFcmToken(token: String) {
        sharedPreferences.setFcmToken(token)
    }

    override fun getFcmToken(): String? {
        return sharedPreferences.getFcmToken()
    }

    override fun setRefreshToken(token: String) {
        return sharedPreferences.setRefreshToken(token)
    }

    override fun getRefreshToken(): String? {
        return sharedPreferences.getRefreshToken()
    }

    override fun setAccessToken(token: String) {
        return sharedPreferences.setAccessToken(token)
    }

    override fun getAccessToken(): String? {
        return sharedPreferences.getAccessToken()
    }
}