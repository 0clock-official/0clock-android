package com.xyz.oclock.core.data.repository

interface DeviceStateRepository {

    fun isFirstRun(): Boolean

    fun setNotFirstRun()

    fun isFirstLogin(): Boolean

    fun setNotFirstLogin()
}