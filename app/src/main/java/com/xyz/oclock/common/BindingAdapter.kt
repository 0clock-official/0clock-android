package com.xyz.oclock.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @BindingAdapter("load_image")
    @JvmStatic
    fun ImageView.loadImage(res: Int?) = Glide.with(context).load(res).into(this)
}