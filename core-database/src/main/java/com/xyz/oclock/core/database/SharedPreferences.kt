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
