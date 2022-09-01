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
import java.util.regex.Pattern


object BindingAdapter {

    @JvmStatic
    @BindingAdapter("toast")
    fun View.bindToast(text: String?) {
        if(!text.isNullOrEmpty()) {
            Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
        }
    }


    @BindingAdapter("load_image")
    @JvmStatic
    fun ImageView.loadImage(res: Int?) = Glide.with(context).load(res).into(this)

    @BindingAdapter("bind_square_layout")
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

    @BindingAdapter("bind_password_format")
    @JvmStatic
    fun TextView.bindPasswordFormat(pw: String) {
        var pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"
        var pattern = Pattern.compile(pwPattern)
        if (pattern.matcher(pw).matches()) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
            if (pw.length < 8 || pw.length > 20) {
                this.text = context.getText(R.string.error_password_format_1)
                return
            }
            pwPattern = "([A-Za-z])"
            pattern = Pattern.compile(pwPattern)
            if (!pattern.matcher(pw).find()) {
                this.text = context.getText(R.string.error_password_format_3)
                return
            }
            pwPattern = "([0-9])"
            pattern = Pattern.compile(pwPattern)
            if (!pattern.matcher(pw).find()) {
                this.text = context.getText(R.string.error_password_format_2)
                return
            }
        }
    }
}