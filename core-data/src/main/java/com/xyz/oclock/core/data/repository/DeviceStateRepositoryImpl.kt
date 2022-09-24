package com.xyz.oclock.core.data.repository

import com.xyz.oclock.core.database.SharedPreferences
import javax.inject.Inject

class DeviceStateRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): DeviceStateRepository {

    override fun isFirstRun(): Boolean {
        return sharedPreferences.getFirstRun()
    }

    override fun setNotFirstRun() {
        sharedPreferences.setFirstRun(false)
    }

    override fun isFirstLogin(): Boolean {
        return sharedPreferences.getFirstLogin()
    }

    override fun setNotFirstLogin() {
        return sharedPreferences.setFirstLogin(false)
    }
}