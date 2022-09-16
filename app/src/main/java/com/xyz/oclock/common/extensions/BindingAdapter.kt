package com.xyz.oclock.common.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.OnThrottleClickListener


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
    fun View.onThrottleClick(listener: View.OnClickListener) {
        setOnClickListener(OnThrottleClickListener(listener))
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun View.visibility(visible: Boolean?) {
        if (visible == true) {
            this.visibility = View.VISIBLE
        } else if (visible == false) {
            this.visibility = View.INVISIBLE
        } else {
            this.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("spinnerAdapter")
    fun AutoCompleteTextView.setAdapter(items: Array<String>) {
        val adapter = ArrayAdapter(this.context, R.layout.item_select_text, items)
        this.setAdapter(adapter)
    }

    @JvmStatic
    @BindingAdapter("dropDownBackground")
    fun AutoCompleteTextView.setDropDownBackground(res: Drawable) {
        dropDownVerticalOffset = 10.toPx
        this.setDropDownBackgroundDrawable(res)
    }

    @JvmStatic
    @BindingAdapter("onItemClicked")
    fun AutoCompleteTextView.setOnItemClicked(onItemClickListener: AdapterView.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}