package com.xyz.oclock.core.data.repository

interface DeviceStateRepository {

    fun isFirstRun(): Boolean

    fun noLongerFirstRun()

    fun isFirstLogin(): Boolean

    fun noLongerFirstLogin()
}