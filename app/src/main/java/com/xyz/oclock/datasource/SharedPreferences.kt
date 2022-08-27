package com.xyz.oclock.datasource

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreferences constructor(context: Context) {

    companion object {
        private const val FILE_NAME = "oclock_encrypted_settings"
        private const val FCM_TOKEN = "fcm_token"
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

    fun setFcmToken(token: String) {
        editor.putString(FCM_TOKEN, token)
        editor.commit()
    }

    fun getFcmToken(): String? {
        return sharedPreferences.getString(FCM_TOKEN, null)
    }

}