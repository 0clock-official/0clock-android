package com.xyz.oclock.common.extensions

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.xyz.oclock.common.utils.OnThrottleClickListener

fun ProgressBar.smoothProgress(percent: Int){
    val animation = ObjectAnimator.ofInt(this, "progress", percent)
    animation.duration = 400
    animation.interpolator = DecelerateInterpolator()
    animation.start()
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.onThrottleClick(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

fun Context.hasPermissions(permissions: Array<String>): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}
