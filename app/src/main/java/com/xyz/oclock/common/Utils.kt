package com.xyz.oclock.common

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar

fun ProgressBar.smoothProgress(percent: Int){
    val animation = ObjectAnimator.ofInt(this, "progress", percent)
    animation.duration = 400
    animation.interpolator = DecelerateInterpolator()
    animation.start()
}