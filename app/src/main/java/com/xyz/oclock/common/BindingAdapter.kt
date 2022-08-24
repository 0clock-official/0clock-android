package com.xyz.oclock.common

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object BindingAdapter {

    @BindingAdapter("load_image")
    @JvmStatic
    fun ImageView.loadImage(res: Int?) = Glide.with(context).load(res).into(this)

    @BindingAdapter("bind_square_layout")
    @JvmStatic
    fun LinearLayout.setSquareLayout(dummy: Any?) {
        val width = this.display.width
        val height = this.display.height
        val ratio = if (width > height) {
            width.toFloat()/height
        } else {
            height.toFloat()/width
        }

        println("test $ratio")
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
}