package com.xyz.oclock.common.extensions

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.whatif.whatIfNotNullAs
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.OnThrottleClickListener
import java.util.logging.Handler


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

    @JvmStatic
    @BindingAdapter("smoothProgress")
    fun ProgressBar.smoothProgress(percent: Int){
        val animation = ObjectAnimator.ofInt(this, "progress", percent)
        animation.duration = 300
        animation.start()
    }

    @JvmStatic
    @BindingAdapter("clockTimer")
    fun TextView.clockTimer(second: Int) {
        this.text = second.toTimeClock()
    }

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        adapter?.let {
            view.adapter = adapter.apply {
//                stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
        }
    }

    @JvmStatic
    @BindingAdapter("submitList")
    fun bindSubmitList(view: RecyclerView, itemList: List<Any>?) {
        view.itemAnimator = null
        view.adapter.whatIfNotNullAs<BindingListAdapter<Any, *>> { adapter ->
            adapter.submitList(itemList)
        }
    }
}