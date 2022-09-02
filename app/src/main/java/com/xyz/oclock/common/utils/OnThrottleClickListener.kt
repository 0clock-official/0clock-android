package com.xyz.oclock.common.utils

import android.view.View

class OnThrottleClickListener(
    private val onClickListener: View.OnClickListener,
    private val interval: Long = 300L
) : View.OnClickListener {

    private var clickable = true

    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.run {
                postDelayed({
                    clickable = true
                }, interval)
                onClickListener.onClick(v)
            }
        }
    }
}