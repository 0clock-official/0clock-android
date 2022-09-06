package com.xyz.oclock.common.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.OnThrottleClickListener
import java.util.regex.Pattern


object BindingAdapter {

    @JvmStatic
    @BindingAdapter("toast")
    fun View.bindToast(text: String?) {
        if(!text.isNullOrEmpty()) {
            Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
        }
    }

    @BindingAdapter("loadImage")
    @JvmStatic
    fun ImageView.loadImage(res: Int?) = Glide.with(context).load(res).into(this)

    @BindingAdapter("setSquareLayout")
    @JvmStatic
    fun CardView.setSquareLayout(dummy: Any?) {
        val width = this.display.width
        val height = this.display.height
        val ratio = if (width > height) {
            width.toFloat()/height
        } else {
            height.toFloat()/width
        }

        if (ratio > 1.8) {
            val layoutParams: ViewGroup.LayoutParams = this.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = 0
            this.layoutParams = layoutParams
        } else {
            val layoutParams: ViewGroup.LayoutParams = this.layoutParams
            layoutParams.width = (this.display.width * 0.5).toInt()
            layoutParams.height = 0
            this.layoutParams = layoutParams
        }
    }

    @JvmStatic
    @BindingAdapter("onThrottleClick")
    fun View.onThrottleClick(action: (v: View) -> Unit) {
        val listener = View.OnClickListener { action(it) }
        setOnClickListener(OnThrottleClickListener(listener))
    }
}