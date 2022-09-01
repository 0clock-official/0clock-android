package com.xyz.oclock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xyz.oclock.common.utils.OClockFirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fcm = Intent(applicationContext, OClockFirebaseMessagingService::class.java)
        startService(fcm)
    }
}