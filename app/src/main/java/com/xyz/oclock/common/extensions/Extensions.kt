package com.xyz.oclock.common.extensions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.util.Base64
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.xyz.oclock.common.utils.OnThrottleClickListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


val Number.toPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics).toInt()

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.onThrottleClick(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

fun Context.hasPermissions(permissions: Array<String>): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun <T> Flow<T>.throttle(windowDuration: Long = 300L): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > windowDuration) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}

fun File.toBase64(): String? {
    var inputStream: InputStream? = null
    val data: ByteArray
    var result: String? = null
    try {
        inputStream = FileInputStream(path)
        data = ByteArray(inputStream.available())
        inputStream.read(data)
        result = Base64.encodeToString(data, Base64.NO_WRAP)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (null != inputStream) {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return result
}

fun Int.toTimeClock(): String {
    return if (this <= 59) {
        if(this < 10) {
            "0:0$this"
        } else {
            "0:$this"
        }
    } else {
        val min: Int = this/60
        val sec: Int = this - (min * 60)
        if(sec > 0) {
            if(sec < 10) {
                "$min:0$sec"
            } else {
                "$min:$sec"
            }
        } else {
            "$min:00"
        }
    }
}