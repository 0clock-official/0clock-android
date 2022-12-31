package com.xyz.oclock.common.utils

import android.content.Context
import android.content.Intent
import com.xyz.oclock.core.data.repository.TokenRepository
import com.xyz.oclock.ui.splash.SplashActivity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

class LogoutHelper @Inject constructor (
    private val tokenRepository: TokenRepository,
    private val context: Context
) {

    private fun clearInfo() {
        tokenRepository.clear()
    }

    private fun clearDataBase() {

    }

    fun logout(msg: String) {
        Timber.d(TAG + "logout")
        clearInfo()
        clearDataBase()
        startLoginFlow()
    }

    fun forcedLogout() {
        clearInfo()
        clearDataBase()
        startLoginFlow()
    }

    private fun startLoginFlow() {
        val intent = Intent(context, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    companion object {
        private val TAG = LogoutHelper::class.java.simpleName
    }
}
