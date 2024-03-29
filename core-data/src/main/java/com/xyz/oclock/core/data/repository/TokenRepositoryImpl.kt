package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.database.SharedPreferences
import com.xyz.oclock.core.model.CommonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val commonRepository: CommonRepository,
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
        return "Bearer " + sharedPreferences.getAccessToken()
    }

    override fun getSocketAccessToken(): String? {
        return sharedPreferences.getAccessToken()
    }

    override fun clear() {
        return sharedPreferences.clear()
    }

}