package com.xyz.oclock.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.xyz.oclock.R

class DefaultDialog(
    context: Context,
    private val layoutResId: Int,
    private val message: String?,
    private val singleButtonText: String? = null,
    private val singleButtonListener: View.OnClickListener?,
    private val rightButtonText: String?,
    private val rightButtonListener: View.OnClickListener?,
    private val leftButtonText: String?,
    private val leftButtonListener: View.OnClickListener?,
) : Dialog(context) {

    private constructor(builder: Builder) : this(
        builder.context,
        builder.layoutResId,
        builder.message,
        builder.singleButtonText,
        builder.singleButtonListener,
        builder.rightButtonText,
        builder.rightButtonListener,
        builder.leftButtonText,
        builder.leftButtonListener,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(layoutResId)

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        initView()
    }

    @SuppressLint("ResourceAsColor")
    private fun initView() {
        initMessage()
        initSingleButton()
        initRightButton()
        initLeftButton()
    }

    private fun initMessage() {
        val messageTextView = findViewById<TextView>(R.id.default_dialog_message)
        messageTextView?.let { textView ->
            if (message == null || message == "") {
                textView.visibility = View.GONE
            } else {
                textView.visibility = View.VISIBLE
                textView.text = message
            }
        }
    }

    private fun initSingleButton() {
        val singleButtonTextView = findViewById<TextView>(R.id.default_dialog_single_btn)
        singleButtonTextView?.let { textView ->
            if (singleButtonText == null || singleButtonText == "") {
                textView.visibility = View.GONE
            } else {
                textView.visibility = View.VISIBLE
                textView.text = singleButtonText
                textView.setOnClickListener {
                    singleButtonListener?.run {
                        onClick(it)
                    }
                    dismiss()
                }
            }
        }
    }

    private fun initRightButton() {
        val rightButtonTextView = findViewById<TextView>(R.id.default_dialog_right_btn)
        rightButtonTextView?.let { textView ->
            if (rightButtonText == null || rightButtonText == "") {
                textView.visibility = View.GONE
            } else {
                textView.visibility = View.VISIBLE
                textView.text = rightButtonText
                textView.setOnClickListener {
                    rightButtonListener?.run {
                        onClick(it)
                    }
                    dismiss()
                }
            }
        }
    }

    private fun initLeftButton() {
        val leftButtonTextView = findViewById<TextView>(R.id.default_dialog_left_btn)
        leftButtonTextView?.let { textView ->
            if (leftButtonText == null || leftButtonText == "") {
                textView.visibility = View.GONE
            } else {
                textView.visibility = View.VISIBLE
                textView.text = leftButtonText
                textView.setOnClickListener {
                    leftButtonListener?.run {
                        onClick(it)
                    }
                    dismiss()
                }
            }
        }
    }

    companion object {
        inline fun build(context: Context, block: Builder.() -> Unit) =
            Builder(context).apply(block).build()
    }

    class Builder(val context: Context) {
        var layoutResId = R.layout.dialog_default
        var message: String? = null
        var singleButtonText: String? = null
        var singleButtonListener: View.OnClickListener? = null
        var rightButtonText: String? = null
        var rightButtonListener: View.OnClickListener? = null
        var leftButtonText: String? = null
        var leftButtonListener: View.OnClickListener? = null
        fun build() = DefaultDialog(this)
    }
}