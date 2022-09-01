package com.xyz.oclock.common.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


const val CHANNEL_NAME = "OClock"
const val CHANNEL_ID = "OClock_channel_id"

@AndroidEntryPoint
class OClockFirebaseMessagingService: FirebaseMessagingService() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    init {
        val token =  FirebaseMessaging.getInstance().token
        token.addOnCompleteListener {
            if (it.isSuccessful) {
                sharedPreferences.setFcmToken(it.result)
            } else {
                Log.d("FCM Token", "failed")
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sharedPreferences.setFcmToken(token)
        Log.d("FCM new Token", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notificationManager = NotificationManagerCompat.from(applicationContext)

        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)

        val title = message.notification?.title
        val body = message.notification?.body

        builder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_oclock_fcm)

        val notification: Notification = builder.build()
        notificationManager.notify(1, notification)
    }
}