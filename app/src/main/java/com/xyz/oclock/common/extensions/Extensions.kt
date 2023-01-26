package com.xyz.oclock.common.extensions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.SystemClock
import android.util.Base64
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import com.google.android.gms.cast.framework.media.internal.ResourceProvider
import com.xyz.oclock.R
import com.xyz.oclock.common.utils.OnThrottleClickListener
import com.xyz.oclock.core.model.Chat
import com.xyz.oclock.core.model.ChatType
import com.xyz.oclock.core.model.SocketChatResponse
import com.xyz.oclock.core.model.SocketChatType
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


fun SocketChatResponse.asChat(): Chat {
    return Chat(
        message = this.message?: "",
        timeStamp = this.timestamp.convertToLocalTimeMillis(),
        type = this.type?.let {
            when (it) {
                SocketChatType.MESSAGE.name -> {
                    ChatType.CHAT_YOU
                }
                SocketChatType.MESSAGE_OK.name -> {
                    ChatType.NOTHING //임시
                }
                SocketChatType.TIME_CHANGE_REQ.name -> {
                    ChatType.SELECT
                }
                SocketChatType.TIME_CHANGE_ACCEPT.name -> {
                    ChatType.ALERT
                }
                SocketChatType.EXIT_CHATTINGROOM.name -> {
                    ChatType.ALERT
                }
                SocketChatType.EXCEPTION.name -> {
                    ChatType.EXCEPTION
                }
                else -> {
                    ChatType.NOTHING
                }
            }
        }?: ChatType.NOTHING
    )
}

fun String?.convertToLocalTimeMillis(): Long {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.UK)
    return try {
        val date = formatter.parse(this!!)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR_OF_DAY, +9)
        calendar.timeInMillis
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        0
    }
}