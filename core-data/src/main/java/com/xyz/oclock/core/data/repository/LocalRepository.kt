package com.xyz.oclock.core.data.repository

interface LocalRepository {

    fun setFcmToken(token: String)

    fun getFcmToken(): String?

}