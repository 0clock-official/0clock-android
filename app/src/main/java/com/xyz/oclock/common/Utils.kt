package com.xyz.oclock.common

import android.animation.ObjectAnimator
import android.app.Activity
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.Toast

fun ProgressBar.smoothProgress(percent: Int){
    val animation = ObjectAnimator.ofInt(this, "progress", percent)
    animation.duration = 400
    animation.interpolator = DecelerateInterpolator()
    animation.start()
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}