package com.xyz.oclock.core.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface TokenRepository {

    fun setFcmToken(token: String)

    fun getFcmToken(): String?

    fun setRefreshToken(token: String)

    fun getRefreshToken(): String?

    fun setAccessToken(token: String)

    fun getAccessToken(): String?

    fun getSocketAccessToken(): String?

    fun clear()
}