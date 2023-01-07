package com.xyz.oclock.core.database

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferences @Inject constructor(@ApplicationContext context : Context){

    companion object {
        private const val FILE_NAME = "oclock_encrypted_settings"
        private const val FIRST_RUN = "first_run"
        private const val FIRST_LOGIN = "first_login"
        private const val FCM_TOKEN = "fcm_token"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val USER_EMAIL = "user_email"
    }

    private val sharedPreferences by lazy {
        val masterKeyAlias = MasterKey
            .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private var editor = sharedPreferences.edit()

    fun clear() {
        editor.clear()
    }

    fun setFcmToken(token: String) {
        editor.putString(FCM_TOKEN, token)
        editor.commit()
    }

    fun getFcmToken(): String? {
        return sharedPreferences.getString(FCM_TOKEN, null)
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }

    fun setAccessToken(token: String) {
        editor.putString(ACCESS_TOKEN, token)
        editor.commit()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN, null)
    }

    fun setRefreshToken(token: String) {
        editor.putString(REFRESH_TOKEN, token)
        editor.commit()
    }

    fun setFirstRun(isFirst: Boolean) {
        editor.putBoolean(FIRST_RUN, isFirst)
        editor.commit()
    }

    fun getFirstRun(): Boolean {
        return sharedPreferences.getBoolean(FIRST_RUN, true)
    }

    fun setFirstLogin(isFirst: Boolean) {
        editor.putBoolean(FIRST_LOGIN, isFirst)
        editor.commit()
    }

    fun getFirstLogin(): Boolean {
        return sharedPreferences.getBoolean(FIRST_LOGIN, true)
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL, null)
    }

    fun setUserEmail(email: String) {
        editor.putString(USER_EMAIL, email)
        editor.commit()
    }

}
