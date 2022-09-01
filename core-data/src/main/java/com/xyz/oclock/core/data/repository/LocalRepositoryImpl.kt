package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.database.SharedPreferences
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): LocalRepository {

    override fun setFcmToken(token: String) {
        sharedPreferences.setFcmToken(token)
    }

    override fun getFcmToken(): String? {
        return sharedPreferences.getFcmToken()
    }
}